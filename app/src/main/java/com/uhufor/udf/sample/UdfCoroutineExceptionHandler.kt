package com.uhufor.udf.sample

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

interface UdfCoroutineExceptionHandler {
    fun getCoroutineExceptionHandler(): CoroutineContext

    companion object : UdfCoroutineExceptionHandler {
        override fun getCoroutineExceptionHandler(): CoroutineContext {
            return CoroutineExceptionHandler { coroutineContext, throwable ->
                Log.i("Udf", "CoroutineExceptionHandler: $throwable")
            }
        }
    }
}
