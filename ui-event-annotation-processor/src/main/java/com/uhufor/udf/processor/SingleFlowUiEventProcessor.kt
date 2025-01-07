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
import com.google.devtools.ksp.symbol.Modifier
import com.squareup.kotlinpoet.ANY
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.UNIT
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo

/**
 * SingleFlowUiEventProcessor:
 *  - @SingleFlowUiEvent 어노테이션이 달린 public 함수 스캔
 *  - 각 parentClass 별로 디스패처 소스코드 생성
 */
class SingleFlowUiEventProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        // (1) @SingleFlowUiEvent 사용 함수 찾기
        val symbols = resolver
            .getSymbolsWithAnnotation("com.uhufor.udf.annotation.SingleFlowUiEvent")
            .filterIsInstance<KSFunctionDeclaration>()

        if (!symbols.iterator().hasNext()) return emptyList()

        // (2) 클래스별로 함수 목록을 그룹핑
        val parentMap = mutableMapOf<KSClassDeclaration, MutableList<EventHandler>>()

        for (symbol in symbols) {
            val parentClassDecl = symbol.parentDeclaration as? KSClassDeclaration ?: continue

            // 접근 제한자 public 확인

            if (symbol.modifiers.contains(Modifier.PRIVATE) ||
                symbol.modifiers.contains(Modifier.PROTECTED)
            ) {
                logger.warn(
                    "Function ${symbol.simpleName.asString()} is not public: ${symbol.modifiers.joinToString { it.name }}",
                    symbol
                )
                continue
            }

            // 어노테이션 인자(target) 파싱
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

        // (3) 각 parentClass 마다 코드 생성
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
        val dispatcherName = "${parentSimpleName}_Dispatcher"

        val dispatchFun = FunSpec.builder("dispatchEvent")
            .addModifiers(KModifier.PUBLIC)
            .addParameter("instance", parentClassDecl.toClassName())
            .addParameter("event", ANY.copy(nullable = true))
            .returns(UNIT)
            .apply {
                beginControlFlow("when (event)")
                for (handler in handlers) {
                    val param = "event".takeIf { handler.functionParams.isNotEmpty() }.orEmpty()
                    addStatement(
                        "is %L -> instance.%L($param)",
                        handler.eventType, // event class
                        handler.functionName,
                    )
                }
                addStatement("else -> Unit // no-op")
                endControlFlow()
            }
            .build()

        val typeSpec = TypeSpec.objectBuilder(dispatcherName)
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
        val functionParams: List<*>,
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
