package com.uhufor.udf.single

import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SingleFlowUiEvent(
    val target: KClass<*> = Unit::class,
)
