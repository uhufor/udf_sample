package com.uhufor.udf.single

import android.util.Log
import java.lang.reflect.Method

// TODO: Add exception handler
class SingleFlowUiEventDispatcher<UiEvent>(
    targetClass: Class<*>,
) {
    private val methodCache: MutableMap<Class<*>, Method> = mutableMapOf()

    init {
        cacheAnnotatedMethods(targetClass)
    }

    private fun cacheAnnotatedMethods(targetClass: Class<*>) {
        targetClass.declaredMethods
            .filter { it.isAnnotationPresent(SingleFlowUiEvent::class.java) }
            .forEach { method ->
                val annotation = method.getAnnotation(SingleFlowUiEvent::class.java)
                if (annotation != null) {
                    methodCache[annotation.target.java] = method
                }
            }
    }

    fun dispatchByAnnotation(instance: Any, event: UiEvent) {
        val target = event ?: return
        val method = methodCache[target::class.java]

        runCatching {
            method?.let {
                it.isAccessible = true
                if (it.parameterCount == 0) {
                    it.invoke(instance)
                } else {
                    it.invoke(instance, event)
                }
            }
        }.onFailure {
            Log.w("AnnotationHandler", "handleEventByAnnotation: ${it.message}")
        }
    }
}
