package com.uhufor.udf.dispatcher

interface EventDispatcher {
    fun dispatchEvent(instance: Any, event: Any?)
}
