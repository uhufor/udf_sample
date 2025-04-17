package com.uhufor.udf.fragment

import androidx.fragment.app.Fragment
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ArgumentSuperFragment : Fragment() {
    var superIntValue by argumentSuper<Int>()
    var superIntValueNull by argumentSuper<Int?>()
}

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ArgumentSuperFragmentTest {

    @Test
    fun givenSuperInt_whenSetValue_thenSuccess() {
        val fragment = ArgumentSuperFragment()

        fragment.superIntValue = 1
        assertNotNull(fragment.superIntValue)
        // Error
//        fragment.superIntValue = null

        fragment.superIntValueNull = null
        assertNull(fragment.superIntValueNull)

        fragment.superIntValueNull = 2
        assertNotNull(fragment.superIntValueNull)
    }

    @Test
    fun givenSuperInt_whenNotSetValue_thenThrowException() {
        val fragment = ArgumentSuperFragment()

        assertFailsWith(IllegalStateException::class) {
            fragment.superIntValue
        }
        assertNull(fragment.superIntValueNull)
    }
}
