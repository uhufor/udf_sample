package com.uhufor.udf.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSValueParameter
import com.google.devtools.ksp.symbol.Modifier
import com.squareup.kotlinpoet.ANY
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.UNIT
import com.squareup.kotlinpoet.ksp.writeTo

class SingleFlowUiEventProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver
            .getSymbolsWithAnnotation("com.uhufor.udf.annotation.SingleFlowUiEvent")
            .filterIsInstance<KSFunctionDeclaration>()

        if (!symbols.iterator().hasNext()) return emptyList()

        val parentMap = mutableMapOf<KSClassDeclaration, MutableList<EventHandler>>()

        for (symbol in symbols) {
            val parentClassDecl = symbol.parentDeclaration as? KSClassDeclaration ?: continue

            if (symbol.modifiers.contains(Modifier.PRIVATE) ||
                symbol.modifiers.contains(Modifier.PROTECTED)
            ) {
                logger.warn(
                    "Function ${symbol.simpleName.asString()} is not public: ${symbol.modifiers.joinToString { it.name }}",
                    symbol
                )
                continue
            }

            val annotation = symbol.annotations.firstOrNull {
                it.shortName.asString() == "SingleFlowUiEvent"
            } ?: continue

            val targetArg = annotation.arguments.firstOrNull { it.name?.asString() == "target" }
            val eventKSType = targetArg?.value as? KSType ?: continue

            val eventQualifiedName = eventKSType.declaration.qualifiedName?.asString() ?: continue
            val functionName = symbol.simpleName.asString()
            val functionParams = symbol.parameters

            val handler = EventHandler(
                eventType = eventQualifiedName,
                functionName = functionName,
                functionParams = functionParams,
            )

            parentMap.getOrPut(parentClassDecl) { mutableListOf() }.add(handler)
        }

        for ((parentClass, handlers) in parentMap) {
            generateDispatcherForClass(parentClass, handlers)
        }

        return emptyList()
    }

    private fun generateDispatcherForClass(
        parentClassDecl: KSClassDeclaration,
        handlers: List<EventHandler>,
    ) {
        val packageName = parentClassDecl.packageName.asString()
        val parentSimpleName = parentClassDecl.simpleName.asString()
        val dispatcherName = "${parentSimpleName}Dispatcher"

        val dispatcherInterface =
            ClassName("com.uhufor.udf.dispatcher", "GeneratedUiEventDispatcher")

        val dispatchFun = FunSpec.builder("dispatchEvent")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("instance", ANY)
            .addParameter("event", ANY.copy(nullable = true))
            .returns(UNIT)
            .apply {
                addStatement("val handler = instance as? %L ?: return", parentSimpleName)
                beginControlFlow("when (event)")
                for (handler in handlers) {
                    val param = "event".takeIf { handler.functionParams.isNotEmpty() }.orEmpty()
                    addStatement(
                        "is %L -> handler.%L($param)",
                        handler.eventType, // event class
                        handler.functionName,
                    )
                }
                addStatement("else -> Unit // no-op")
                endControlFlow()
            }
            .build()

        val typeSpec = TypeSpec.objectBuilder(dispatcherName)
            .addSuperinterface(dispatcherInterface)
            .addFunction(dispatchFun)
            .build()

        val fileSpec = FileSpec.builder(packageName, dispatcherName)
            .addType(typeSpec)
            .build()

        fileSpec.writeTo(codeGenerator, Dependencies(aggregating = false))
    }

    data class EventHandler(
        val eventType: String,
        val functionName: String,
        val functionParams: List<KSValueParameter>,
    )
}

class SingleFlowUiEventProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return SingleFlowUiEventProcessor(
            environment.codeGenerator,
            environment.logger
        )
    }
}
