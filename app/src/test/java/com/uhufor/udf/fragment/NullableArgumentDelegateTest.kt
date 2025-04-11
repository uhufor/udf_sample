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
internal class TestParcelable(val label: String, val meta: NestedTestParcelable) : Parcelable

@Parcelize
internal class NestedTestParcelable(val size: Long) : Parcelable

internal class TestSerializable(val label: String, val meta: NestedTestSerializable) : Serializable

internal class NestedTestSerializable(val size: Long) : Serializable

internal class NullableArgumentFragment : Fragment() {
    // Primitive
    var boolValue by argument<Boolean>()
    var byteValue by argument<Byte>()
    var charValue by argument<Char>()
    var shortValue by argument<Short>()
    var intValue by argument<Int>()
    var longValue by argument<Long>()
    var floatValue by argument<Float>()
    var doubleValue by argument<Double>()
    var stringValue by argument<String>()
    var charSequenceValue by argument<CharSequence>()

    // Primitive Array
    var boolArrayValue by argument<BooleanArray>()
    var byteArrayValue by argument<ByteArray>()
    var shortArrayValue by argument<ShortArray>()
    var charArrayValue by argument<CharArray>()
    var intArrayValue by argument<IntArray>()
    var longArrayValue by argument<LongArray>()
    var floatArrayValue by argument<FloatArray>()
    var doubleArrayValue by argument<DoubleArray>()

    // Typed Array
    var stringArrayValue by argument<Array<String>>()
    var parcelableArrayValue by argument<Array<TestParcelable>>()
    var charSequenceArrayValue by argument<Array<CharSequence>>()

    // Typed ArrayList
    var stringListValue by argument<List<String>>()
    var intListValue by argument<List<Int>>()
    var charSequenceListValue by argument<List<CharSequence>>()
    var parcelableListValue by argument<List<TestParcelable>>()

    // Typed Class
    var parcelableValue by argument<Parcelable>()
    var serializableValue by argument<Serializable>()
}

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
internal class NullableArgumentDelegateTest {

    @Test
    fun givenBooleanArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.boolValue = true

        assertEquals(true, fragment.arguments?.getBoolean("boolValue"))
        assertEquals(true, fragment.boolValue)
    }

    @Test
    fun givenByteArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()
        val byteVal: Byte = 100

        fragment.byteValue = byteVal

        assertEquals(byteVal, fragment.arguments?.getByte("byteValue"))
        assertEquals(byteVal, fragment.byteValue)
    }

    @Test
    fun givenCharArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.charValue = 'Z'

        assertEquals('Z', fragment.arguments?.getChar("charValue"))
        assertEquals('Z', fragment.charValue)
    }

    @Test
    fun givenShortArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        val shortVal: Short = 123
        fragment.shortValue = shortVal

        assertEquals(shortVal, fragment.arguments?.getShort("shortValue"))
        assertEquals(shortVal, fragment.shortValue)
    }

    @Test
    fun givenIntArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.intValue = 999

        assertEquals(999, fragment.arguments?.getInt("intValue"))
        assertEquals(999, fragment.intValue)
    }

    @Test
    fun givenLongArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.longValue = 123456789L

        assertEquals(123456789L, fragment.arguments?.getLong("longValue"))
        assertEquals(123456789L, fragment.longValue)
    }

    @Test
    fun givenFloatArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.floatValue = 3.14f

        assertEquals(3.14f, fragment.arguments?.getFloat("floatValue"))
        assertEquals(3.14f, fragment.floatValue)
    }

    @Test
    fun givenDoubleArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.doubleValue = 99.99

        assertEquals(99.99, fragment.arguments?.getDouble("doubleValue", 0.0))
        assertEquals(99.99, fragment.doubleValue)
    }

    @Test
    fun givenStringArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.stringValue = "Hello"

        assertEquals("Hello", fragment.arguments?.getString("stringValue"))
        assertEquals("Hello", fragment.stringValue)
    }

    @Test
    fun givenCharSequenceArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.charSequenceValue = "CharSeq"

        assertEquals("CharSeq", fragment.arguments?.getCharSequence("charSequenceValue"))
        assertEquals("CharSeq", fragment.charSequenceValue)
    }

    @Test
    fun givenBooleanArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.boolArrayValue = booleanArrayOf(true, false, true)

        val stored = fragment.arguments?.getBooleanArray("boolArrayValue")
        assertArrayEquals(booleanArrayOf(true, false, true), stored)
        assertArrayEquals(booleanArrayOf(true, false, true), fragment.boolArrayValue)
    }

    @Test
    fun givenByteArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.byteArrayValue = byteArrayOf(1, 2, 3)

        val stored = fragment.arguments?.getByteArray("byteArrayValue")
        assertArrayEquals(byteArrayOf(1, 2, 3), stored)
        assertArrayEquals(byteArrayOf(1, 2, 3), fragment.byteArrayValue)
    }

    @Test
    fun givenShortArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.shortArrayValue = shortArrayOf(10, 20, 30)

        val stored = fragment.arguments?.getShortArray("shortArrayValue")
        assertArrayEquals(shortArrayOf(10, 20, 30), stored)
        assertArrayEquals(shortArrayOf(10, 20, 30), fragment.shortArrayValue)
    }

    @Test
    fun givenCharArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.charArrayValue = charArrayOf('A', 'B', 'C')

        val stored = fragment.arguments?.getCharArray("charArrayValue")
        assertArrayEquals(charArrayOf('A', 'B', 'C'), stored)
        assertArrayEquals(charArrayOf('A', 'B', 'C'), fragment.charArrayValue)
    }

    @Test
    fun givenIntArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.intArrayValue = intArrayOf(1, 2, 3)

        val stored = fragment.arguments?.getIntArray("intArrayValue")
        assertArrayEquals(intArrayOf(1, 2, 3), stored)
        assertArrayEquals(intArrayOf(1, 2, 3), fragment.intArrayValue)
    }

    @Test
    fun givenLongArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.longArrayValue = longArrayOf(100L, 200L, 300L)

        val stored = fragment.arguments?.getLongArray("longArrayValue")
        assertArrayEquals(longArrayOf(100L, 200L, 300L), stored)
        assertArrayEquals(longArrayOf(100L, 200L, 300L), fragment.longArrayValue)
    }

    @Test
    fun givenFloatArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.floatArrayValue = floatArrayOf(1.1f, 2.2f, 3.3f)

        val stored = fragment.arguments?.getFloatArray("floatArrayValue")
        assertArrayEquals(floatArrayOf(1.1f, 2.2f, 3.3f), stored, 0.0f)
        assertArrayEquals(floatArrayOf(1.1f, 2.2f, 3.3f), fragment.floatArrayValue, 0.0f)
    }

    @Test
    fun givenDoubleArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.doubleArrayValue = doubleArrayOf(1.11, 2.22, 3.33)

        val stored = fragment.arguments?.getDoubleArray("doubleArrayValue")
        assertArrayEquals(doubleArrayOf(1.11, 2.22, 3.33), stored, 0.0)
        assertArrayEquals(doubleArrayOf(1.11, 2.22, 3.33), fragment.doubleArrayValue, 0.0)
    }

    @Test
    fun givenStringArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.stringArrayValue = arrayOf("one", "two", "three")

        val stored = fragment.arguments?.getStringArray("stringArrayValue")
        assertArrayEquals(arrayOf("one", "two", "three"), stored)
        assertArrayEquals(arrayOf("one", "two", "three"), fragment.stringArrayValue)
    }

    @Test
    fun givenParcelableArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()
        val arrayVal = arrayOf(
            TestParcelable("a", NestedTestParcelable(500L)),
            TestParcelable("b", NestedTestParcelable(200L))
        )

        fragment.parcelableArrayValue = arrayVal

        val stored = BundleCompat.getParcelableArray(
            fragment.arguments!!,
            "parcelableArrayValue",
            TestParcelable::class.java
        )
        assertNotNull(stored)
        assertEquals((stored[0] as TestParcelable).label, arrayVal[0].label)
        assertEquals((stored[0] as TestParcelable).meta, arrayVal[0].meta)
        assertEquals((stored[1] as TestParcelable).label, arrayVal[1].label)
        assertEquals((stored[1] as TestParcelable).meta, arrayVal[1].meta)

        val retrievedArray = fragment.parcelableArrayValue
        assertNotNull(retrievedArray)
        assertEquals(retrievedArray[0].label, arrayVal[0].label)
        assertEquals(retrievedArray[0].meta, arrayVal[0].meta)
        assertEquals(retrievedArray[1].label, arrayVal[1].label)
        assertEquals(retrievedArray[1].meta, arrayVal[1].meta)
    }

    @Test
    fun givenCharSequenceArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.charSequenceArrayValue = arrayOf("x", "y")

        val stored = fragment.arguments?.getCharSequenceArray("charSequenceArrayValue")
        assertArrayEquals(arrayOf("x", "y"), stored)
        assertArrayEquals(arrayOf("x", "y"), fragment.charSequenceArrayValue)
    }

    @Test
    fun givenStringListArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.stringListValue = arrayListOf("alpha", "beta")

        val stored = fragment.arguments?.getStringArrayList("stringListValue")
        assertEquals(listOf("alpha", "beta"), stored)
        assertEquals(listOf("alpha", "beta"), fragment.stringListValue)
    }

    @Test
    fun givenIntListArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.intListValue = arrayListOf(100, 200)

        val stored = fragment.arguments?.getIntegerArrayList("intListValue")
        assertEquals(listOf(100, 200), stored)
        assertEquals(listOf(100, 200), fragment.intListValue)
    }

    @Test
    fun givenCharSequenceListArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.charSequenceListValue = arrayListOf("c1", "c2")

        val stored = fragment.arguments?.getCharSequenceArrayList("charSequenceListValue")
        assertEquals(listOf("c1", "c2"), stored)
        assertEquals(listOf("c1", "c2"), fragment.charSequenceListValue)
    }

    @Test
    fun givenParcelableListArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()
        val listVal = arrayListOf(
            TestParcelable("a", NestedTestParcelable(500L)),
            TestParcelable("b", NestedTestParcelable(200L))
        )

        fragment.parcelableListValue = listVal

        val stored = BundleCompat.getParcelableArrayList<TestParcelable>(
            fragment.arguments!!,
            "parcelableListValue",
            TestParcelable::class.java
        )
        assertNotNull(stored)
        assertEquals(listVal[0].label, stored[0].label)
        assertEquals(listVal[0].meta, stored[0].meta)
        assertEquals(listVal[1].label, stored[1].label)
        assertEquals(listVal[1].meta, stored[1].meta)

        val retrievedList = fragment.parcelableListValue
        assertNotNull(retrievedList)
        assertEquals(listVal[0].label, retrievedList[0].label)
        assertEquals(listVal[0].meta, retrievedList[0].meta)
        assertEquals(listVal[1].label, retrievedList[1].label)
        assertEquals(listVal[1].meta, retrievedList[1].meta)
    }

    @Test
    fun givenParcelableArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.parcelableValue = TestParcelable("parcelData", NestedTestParcelable(100L))

        val stored = BundleCompat.getParcelable<TestParcelable>(
            fragment.arguments!!,
            "parcelableValue",
            TestParcelable::class.java
        )
        assertNotNull(stored)
        assertEquals("parcelData", stored.label)
        assertEquals(100L, stored.meta.size)

        val retrieved = fragment.parcelableValue as TestParcelable
        assertEquals("parcelData", retrieved.label)
        assertEquals(100L, retrieved.meta.size)
    }

    @Test
    fun givenSerializableArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NullableArgumentFragment()

        fragment.serializableValue = TestSerializable("serialData", NestedTestSerializable(200L))

        val stored = BundleCompat.getSerializable<TestSerializable>(
            fragment.arguments!!,
            "serializableValue",
            TestSerializable::class.java
        )
        assertNotNull(stored)
        assertEquals("serialData", stored.label)
        assertEquals(200L, stored.meta.size)

        val retrieved = fragment.serializableValue as TestSerializable
        assertEquals("serialData", retrieved.label)
        assertEquals(200L, retrieved.meta.size)
    }

    @Test
    fun givenAnyArgument_whenNotSet_thenShouldReturnNull() {
        val fragment = NullableArgumentFragment()

        assertNull(fragment.boolValue)
        assertNull(fragment.byteValue)
        assertNull(fragment.charValue)
        assertNull(fragment.shortValue)
        assertNull(fragment.intValue)
        assertNull(fragment.longValue)
        assertNull(fragment.floatValue)
        assertNull(fragment.doubleValue)
        assertNull(fragment.stringValue)
        assertNull(fragment.charSequenceValue)
        assertNull(fragment.boolArrayValue)
        assertNull(fragment.byteArrayValue)
        assertNull(fragment.shortArrayValue)
        assertNull(fragment.charArrayValue)
        assertNull(fragment.intArrayValue)
        assertNull(fragment.longArrayValue)
        assertNull(fragment.floatArrayValue)
        assertNull(fragment.doubleArrayValue)
        assertNull(fragment.stringArrayValue)
        assertNull(fragment.parcelableArrayValue)
        assertNull(fragment.charSequenceArrayValue)
        assertNull(fragment.stringListValue)
        assertNull(fragment.intListValue)
        assertNull(fragment.charSequenceListValue)
        assertNull(fragment.parcelableListValue)
        assertNull(fragment.parcelableValue)
        assertNull(fragment.serializableValue)
    }
}
