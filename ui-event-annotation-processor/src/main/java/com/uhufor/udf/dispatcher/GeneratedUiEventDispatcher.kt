package com.uhufor.udf.dispatcher

interface GeneratedUiEventDispatcher {
    fun dispatchEvent(instance: Any, event: Any?)
}

object NoOpGeneratedUiEventDispatcher : GeneratedUiEventDispatcher {
    override fun dispatchEvent(instance: Any, event: Any?) {
        throw NotImplementedError("Replace this by generated dispatcher.")
    }
}
