package com.uhufor.udf.annotation

import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SingleFlowUiEvent(
    val target: KClass<out Any>,
)
