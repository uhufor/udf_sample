package com.uhufor.udf

import android.app.Application
import com.uhufor.udf.debug.DebugLabelInitializer

class UdfApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DebugLabelInitializer.initialize(this)
    }
}
