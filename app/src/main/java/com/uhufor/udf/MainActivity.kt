package com.uhufor.udf

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.uhufor.udf.single.sample.SingleFlowCounterFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()
        setSingleFlowCounterFragment(savedInstanceState)
    }

    private fun setSingleFlowCounterFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SingleFlowCounterFragment())
                .commit()
        }
    }
}
