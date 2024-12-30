package com.uhufor.udf.sample

import kotlinx.coroutines.CoroutineScope

interface UdfCoroutineScopeOwner {
    fun getCoroutineScope(): CoroutineScope
}
