package com.uhufor.udf.fragment

import android.os.Parcelable
import androidx.core.os.BundleCompat
import androidx.fragment.app.Fragment
import kotlinx.parcelize.Parcelize
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.Serializable
import kotlin.test.assertNotNull

@Parcelize
class TestParcelable(val data: String, val nested: NestedTestParcelable) : Parcelable

@Parcelize
class NestedTestParcelable(val duty: Long) : Parcelable

class TestSerializable(val data: String, val nested: NestedTestSerializable) : Serializable

class NestedTestSerializable(val duty: Long) : Serializable

class ArgumentDelegateTestNullableFragment : Fragment() {
    var boolVal by argument<Boolean>()
    var byteVal by argument<Byte>()
    var charVal by argument<Char>()
    var shortVal by argument<Short>()
    var intVal by argument<Int>()
    var longVal by argument<Long>()
    var floatVal by argument<Float>()
    var doubleVal by argument<Double>()
    var stringVal by argument<String>()
    var charSeqVal by argument<CharSequence>()
    var boolArrayVal by argument<BooleanArray>()
    var byteArrayVal by argument<ByteArray>()
    var shortArrayVal by argument<ShortArray>()
    var charArrayVal by argument<CharArray>()
    var intArrayVal by argument<IntArray>()
    var longArrayVal by argument<LongArray>()
    var floatArrayVal by argument<FloatArray>()
    var doubleArrayVal by argument<DoubleArray>()
    var stringArrayVal by argument<Array<String>>()
    var parcelableArrayVal by argument<Array<TestParcelable>>()
    var charSeqArrayVal by argument<Array<CharSequence>>()
    var stringListVal by argument<List<String>>()
    var intListVal by argument<List<Int>>()
    var charSeqListVal by argument<List<CharSequence>>()
    var parcelableListVal by argument<List<TestParcelable>>()
    var parcelableVal by argument<Parcelable>()
    var serializableVal by argument<Serializable>()
}

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ArgumentDelegateAllTypesTest {

    @Test
    fun givenBooleanArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.boolVal = true

        assertEquals(true, fragment.arguments?.getBoolean("boolVal"))
        assertEquals(true, fragment.boolVal)
    }

    @Test
    fun givenByteArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()
        val byteVal: Byte = 100

        fragment.byteVal = byteVal

        assertEquals(byteVal, fragment.arguments?.getByte("byteVal"))
        assertEquals(byteVal, fragment.byteVal)
    }

    @Test
    fun givenCharArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.charVal = 'Z'

        assertEquals('Z', fragment.arguments?.getChar("charVal"))
        assertEquals('Z', fragment.charVal)
    }

    @Test
    fun givenShortArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        val shortVal: Short = 123
        fragment.shortVal = shortVal

        assertEquals(shortVal, fragment.arguments?.getShort("shortVal"))
        assertEquals(shortVal, fragment.shortVal)
    }

    @Test
    fun givenIntArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.intVal = 999

        assertEquals(999, fragment.arguments?.getInt("intVal"))
        assertEquals(999, fragment.intVal)
    }

    @Test
    fun givenLongArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.longVal = 123456789L

        assertEquals(123456789L, fragment.arguments?.getLong("longVal"))
        assertEquals(123456789L, fragment.longVal)
    }

    @Test
    fun givenFloatArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.floatVal = 3.14f

        assertEquals(3.14f, fragment.arguments?.getFloat("floatVal"))
        assertEquals(3.14f, fragment.floatVal)
    }

    @Test
    fun givenDoubleArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.doubleVal = 99.99

        assertEquals(99.99, fragment.arguments?.getDouble("doubleVal", 0.0))
        assertEquals(99.99, fragment.doubleVal)
    }

    @Test
    fun givenStringArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.stringVal = "Hello"

        assertEquals("Hello", fragment.arguments?.getString("stringVal"))
        assertEquals("Hello", fragment.stringVal)
    }

    @Test
    fun givenCharSequenceArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.charSeqVal = "CharSeq"

        assertEquals("CharSeq", fragment.arguments?.getCharSequence("charSeqVal"))
        assertEquals("CharSeq", fragment.charSeqVal)
    }

    @Test
    fun givenBooleanArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.boolArrayVal = booleanArrayOf(true, false, true)

        val stored = fragment.arguments?.getBooleanArray("boolArrayVal")
        assertArrayEquals(booleanArrayOf(true, false, true), stored)
        assertArrayEquals(booleanArrayOf(true, false, true), fragment.boolArrayVal)
    }

    @Test
    fun givenByteArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.byteArrayVal = byteArrayOf(1, 2, 3)

        val stored = fragment.arguments?.getByteArray("byteArrayVal")
        assertArrayEquals(byteArrayOf(1, 2, 3), stored)
        assertArrayEquals(byteArrayOf(1, 2, 3), fragment.byteArrayVal)
    }

    @Test
    fun givenShortArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.shortArrayVal = shortArrayOf(10, 20, 30)

        val stored = fragment.arguments?.getShortArray("shortArrayVal")
        assertArrayEquals(shortArrayOf(10, 20, 30), stored)
        assertArrayEquals(shortArrayOf(10, 20, 30), fragment.shortArrayVal)
    }

    @Test
    fun givenCharArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.charArrayVal = charArrayOf('A', 'B', 'C')

        val stored = fragment.arguments?.getCharArray("charArrayVal")
        assertArrayEquals(charArrayOf('A', 'B', 'C'), stored)
        assertArrayEquals(charArrayOf('A', 'B', 'C'), fragment.charArrayVal)
    }

    @Test
    fun givenIntArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.intArrayVal = intArrayOf(1, 2, 3)

        val stored = fragment.arguments?.getIntArray("intArrayVal")
        assertArrayEquals(intArrayOf(1, 2, 3), stored)
        assertArrayEquals(intArrayOf(1, 2, 3), fragment.intArrayVal)
    }

    @Test
    fun givenLongArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.longArrayVal = longArrayOf(100L, 200L, 300L)

        val stored = fragment.arguments?.getLongArray("longArrayVal")
        assertArrayEquals(longArrayOf(100L, 200L, 300L), stored)
        assertArrayEquals(longArrayOf(100L, 200L, 300L), fragment.longArrayVal)
    }

    @Test
    fun givenFloatArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.floatArrayVal = floatArrayOf(1.1f, 2.2f, 3.3f)

        val stored = fragment.arguments?.getFloatArray("floatArrayVal")
        assertArrayEquals(floatArrayOf(1.1f, 2.2f, 3.3f), stored, 0.0f)
        assertArrayEquals(floatArrayOf(1.1f, 2.2f, 3.3f), fragment.floatArrayVal, 0.0f)
    }

    @Test
    fun givenDoubleArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.doubleArrayVal = doubleArrayOf(1.11, 2.22, 3.33)

        val stored = fragment.arguments?.getDoubleArray("doubleArrayVal")
        assertArrayEquals(doubleArrayOf(1.11, 2.22, 3.33), stored, 0.0)
        assertArrayEquals(doubleArrayOf(1.11, 2.22, 3.33), fragment.doubleArrayVal, 0.0)
    }

    @Test
    fun givenStringArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.stringArrayVal = arrayOf("one", "two", "three")

        val stored = fragment.arguments?.getStringArray("stringArrayVal")
        assertArrayEquals(arrayOf("one", "two", "three"), stored)
        assertArrayEquals(arrayOf("one", "two", "three"), fragment.stringArrayVal)
    }

    @Test
    fun givenParcelableArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()
        val arrayVal = arrayOf(
            TestParcelable("a", NestedTestParcelable(500L)),
            TestParcelable("b", NestedTestParcelable(200L))
        )

        fragment.parcelableArrayVal = arrayVal

        val stored = BundleCompat.getParcelableArray(
            fragment.arguments!!,
            "parcelableArrayVal",
            TestParcelable::class.java
        )
        assertNotNull(stored)
        assertEquals((stored[0] as TestParcelable).data, arrayVal[0].data)
        assertEquals((stored[0] as TestParcelable).nested, arrayVal[0].nested)
        assertEquals((stored[1] as TestParcelable).data, arrayVal[1].data)
        assertEquals((stored[1] as TestParcelable).nested, arrayVal[1].nested)

        val retrievedArray = fragment.parcelableArrayVal
        assertNotNull(retrievedArray)
        assertEquals(retrievedArray[0].data, arrayVal[0].data)
        assertEquals(retrievedArray[0].nested, arrayVal[0].nested)
        assertEquals(retrievedArray[1].data, arrayVal[1].data)
        assertEquals(retrievedArray[1].nested, arrayVal[1].nested)
    }

    @Test
    fun givenCharSequenceArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.charSeqArrayVal = arrayOf("x", "y")

        val stored = fragment.arguments?.getCharSequenceArray("charSeqArrayVal")
        assertArrayEquals(arrayOf("x", "y"), stored)
        assertArrayEquals(arrayOf("x", "y"), fragment.charSeqArrayVal)
    }

    @Test
    fun givenStringListArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.stringListVal = arrayListOf("alpha", "beta")

        val stored = fragment.arguments?.getStringArrayList("stringListVal")
        assertEquals(listOf("alpha", "beta"), stored)
        assertEquals(listOf("alpha", "beta"), fragment.stringListVal)
    }

    @Test
    fun givenIntListArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.intListVal = arrayListOf(100, 200)

        val stored = fragment.arguments?.getIntegerArrayList("intListVal")
        assertEquals(listOf(100, 200), stored)
        assertEquals(listOf(100, 200), fragment.intListVal)
    }

    @Test
    fun givenCharSequenceListArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.charSeqListVal = arrayListOf("c1", "c2")

        val stored = fragment.arguments?.getCharSequenceArrayList("charSeqListVal")
        assertEquals(listOf("c1", "c2"), stored)
        assertEquals(listOf("c1", "c2"), fragment.charSeqListVal)
    }

    @Test
    fun givenParcelableListArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()
        val listVal = arrayListOf(
            TestParcelable("a", NestedTestParcelable(500L)),
            TestParcelable("b", NestedTestParcelable(200L))
        )

        fragment.parcelableListVal = listVal

        val stored = BundleCompat.getParcelableArrayList<TestParcelable>(
            fragment.arguments!!,
            "parcelableListVal",
            TestParcelable::class.java
        )
        assertNotNull(stored)
        assertEquals(listVal[0].data, stored[0].data)
        assertEquals(listVal[0].nested, stored[0].nested)
        assertEquals(listVal[1].data, stored[1].data)
        assertEquals(listVal[1].nested, stored[1].nested)

        val retrievedList = fragment.parcelableListVal
        assertNotNull(retrievedList)
        assertEquals(listVal[0].data, retrievedList[0].data)
        assertEquals(listVal[0].nested, retrievedList[0].nested)
        assertEquals(listVal[1].data, retrievedList[1].data)
        assertEquals(listVal[1].nested, retrievedList[1].nested)
    }

    @Test
    fun givenParcelableArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.parcelableVal = TestParcelable("parcelData", NestedTestParcelable(100L))

        val stored = BundleCompat.getParcelable<TestParcelable>(
            fragment.arguments!!,
            "parcelableVal",
            TestParcelable::class.java
        )
        assertNotNull(stored)
        assertEquals("parcelData", stored.data)
        assertEquals(100L, stored.nested.duty)

        val retrieved = fragment.parcelableVal as TestParcelable
        assertEquals("parcelData", retrieved.data)
        assertEquals(100L, retrieved.nested.duty)
    }

    @Test
    fun givenSerializableArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = ArgumentDelegateTestNullableFragment()

        fragment.serializableVal = TestSerializable("serialData", NestedTestSerializable(200L))

        val stored = BundleCompat.getSerializable<TestSerializable>(
            fragment.arguments!!,
            "serializableVal",
            TestSerializable::class.java
        )
        assertNotNull(stored)
        assertEquals("serialData", stored.data)
        assertEquals(200L, stored.nested.duty)

        val retrieved = fragment.serializableVal as TestSerializable
        assertEquals("serialData", retrieved.data)
        assertEquals(200L, retrieved.nested.duty)
    }

    @Test
    fun givenAnyArgument_whenNotSet_thenShouldReturnNull() {
        val fragment = ArgumentDelegateTestNullableFragment()

        assertNull(fragment.boolVal)
        assertNull(fragment.byteVal)
        assertNull(fragment.charVal)
        assertNull(fragment.shortVal)
        assertNull(fragment.intVal)
        assertNull(fragment.longVal)
        assertNull(fragment.floatVal)
        assertNull(fragment.doubleVal)
        assertNull(fragment.stringVal)
        assertNull(fragment.charSeqVal)
        assertNull(fragment.boolArrayVal)
        assertNull(fragment.byteArrayVal)
        assertNull(fragment.shortArrayVal)
        assertNull(fragment.charArrayVal)
        assertNull(fragment.intArrayVal)
        assertNull(fragment.longArrayVal)
        assertNull(fragment.floatArrayVal)
        assertNull(fragment.doubleArrayVal)
        assertNull(fragment.stringArrayVal)
        assertNull(fragment.parcelableArrayVal)
        assertNull(fragment.charSeqArrayVal)
        assertNull(fragment.stringListVal)
        assertNull(fragment.intListVal)
        assertNull(fragment.charSeqListVal)
        assertNull(fragment.parcelableListVal)
        assertNull(fragment.parcelableVal)
        assertNull(fragment.serializableVal)
    }
}
