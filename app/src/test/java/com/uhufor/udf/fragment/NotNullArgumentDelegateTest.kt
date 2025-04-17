package com.uhufor.udf.fragment

import androidx.core.os.BundleCompat
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
internal class NotNullArgumentDelegateTest {

    @Test
    fun givenBooleanArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()

        fragment.boolValue = true

        assertEquals(true, fragment.arguments?.getBoolean("boolValue"))
        assertEquals(true, fragment.boolValue)
    }

    @Test
    fun givenBooleanArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.boolValue
        }
    }

    @Test
    fun givenByteArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()
        val byteVal: Byte = 100

        fragment.byteValue = byteVal

        assertEquals(byteVal, fragment.arguments?.getByte("byteValue"))
        assertEquals(byteVal, fragment.byteValue)
    }

    @Test
    fun givenByteArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.byteValue
        }
    }

    @Test
    fun givenCharArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()

        fragment.charValue = 'Z'

        assertEquals('Z', fragment.arguments?.getChar("charValue"))
        assertEquals('Z', fragment.charValue)
    }

    @Test
    fun givenCharArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.charValue
        }
    }

    @Test
    fun givenShortArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()
        val shortVal: Short = 123

        fragment.shortValue = shortVal

        assertEquals(shortVal, fragment.arguments?.getShort("shortValue"))
        assertEquals(shortVal, fragment.shortValue)
    }

    @Test
    fun givenShortArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.shortValue
        }
    }

    @Test
    fun givenIntArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()

        fragment.intValue = 999

        assertEquals(999, fragment.arguments?.getInt("intValue"))
        assertEquals(999, fragment.intValue)
    }

    @Test
    fun givenIntArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.intValue
        }
    }

    @Test
    fun givenLongArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()

        fragment.longValue = 123456789L

        assertEquals(123456789L, fragment.arguments?.getLong("longValue"))
        assertEquals(123456789L, fragment.longValue)
    }

    @Test
    fun givenLongArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.longValue
        }
    }

    @Test
    fun givenFloatArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()

        fragment.floatValue = 3.14f

        assertEquals(3.14f, fragment.arguments?.getFloat("floatValue"))
        assertEquals(3.14f, fragment.floatValue)
    }

    @Test
    fun givenFloatArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.floatValue
        }
    }

    @Test
    fun givenDoubleArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()

        fragment.doubleValue = 99.99

        assertEquals(99.99, fragment.arguments?.getDouble("doubleValue"))
        assertEquals(99.99, fragment.doubleValue, 0.0)
    }

    @Test
    fun givenDoubleArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.doubleValue
        }
    }

    @Test
    fun givenStringArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()

        fragment.stringValue = "Hello"

        assertEquals("Hello", fragment.arguments?.getString("stringValue"))
        assertEquals("Hello", fragment.stringValue)
    }

    @Test
    fun givenStringArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.stringValue
        }
    }

    @Test
    fun givenCharSequenceArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()

        fragment.charSequenceValue = "CharSeq"

        assertEquals("CharSeq", fragment.arguments?.getCharSequence("charSequenceValue"))
        assertEquals("CharSeq", fragment.charSequenceValue)
    }

    @Test
    fun givenCharSequenceArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.charSequenceValue
        }
    }

    @Test
    fun givenBooleanArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()
        val arr = booleanArrayOf(true, false, true)

        fragment.boolArrayValue = arr

        val stored = fragment.arguments?.getBooleanArray("boolArrayValue")
        assertArrayEquals(arr, stored)
        assertArrayEquals(arr, fragment.boolArrayValue)
    }

    @Test
    fun givenBooleanArrayArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.boolArrayValue
        }
    }

    @Test
    fun givenByteArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()
        val arr = byteArrayOf(1, 2, 3)

        fragment.byteArrayValue = arr

        val stored = fragment.arguments?.getByteArray("byteArrayValue")
        assertArrayEquals(arr, stored)
        assertArrayEquals(arr, fragment.byteArrayValue)
    }

    @Test
    fun givenByteArrayArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.byteArrayValue
        }
    }

    @Test
    fun givenShortArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()
        val arr = shortArrayOf(10, 20, 30)

        fragment.shortArrayValue = arr

        val stored = fragment.arguments?.getShortArray("shortArrayValue")
        assertArrayEquals(arr, stored)
        assertArrayEquals(arr, fragment.shortArrayValue)
    }

    @Test
    fun givenShortArrayArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.shortArrayValue
        }
    }

    @Test
    fun givenCharArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()
        val arr = charArrayOf('A', 'B', 'C')

        fragment.charArrayValue = arr

        val stored = fragment.arguments?.getCharArray("charArrayValue")
        assertArrayEquals(arr, stored)
        assertArrayEquals(arr, fragment.charArrayValue)
    }

    @Test
    fun givenCharArrayArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.charArrayValue
        }
    }

    @Test
    fun givenIntArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()
        val arr = intArrayOf(1, 2, 3)

        fragment.intArrayValue = arr

        val stored = fragment.arguments?.getIntArray("intArrayValue")
        assertArrayEquals(arr, stored)
        assertArrayEquals(arr, fragment.intArrayValue)
    }

    @Test
    fun givenIntArrayArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.intArrayValue
        }
    }

    @Test
    fun givenLongArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()
        val arr = longArrayOf(100L, 200L, 300L)

        fragment.longArrayValue = arr

        val stored = fragment.arguments?.getLongArray("longArrayValue")
        assertArrayEquals(arr, stored)
        assertArrayEquals(arr, fragment.longArrayValue)
    }

    @Test
    fun givenLongArrayArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.longArrayValue
        }
    }

    @Test
    fun givenFloatArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()
        val arr = floatArrayOf(1.1f, 2.2f, 3.3f)

        fragment.floatArrayValue = arr

        val stored = fragment.arguments?.getFloatArray("floatArrayValue")
        assertArrayEquals(arr, stored, 0.0f)
        assertArrayEquals(arr, fragment.floatArrayValue, 0.0f)
    }

    @Test
    fun givenFloatArrayArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.floatArrayValue
        }
    }

    @Test
    fun givenDoubleArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()
        val arr = doubleArrayOf(1.11, 2.22, 3.33)

        fragment.doubleArrayValue = arr

        val stored = fragment.arguments?.getDoubleArray("doubleArrayValue")
        assertArrayEquals(arr, stored, 0.0)
        assertArrayEquals(arr, fragment.doubleArrayValue, 0.0)
    }

    @Test
    fun givenDoubleArrayArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.doubleArrayValue
        }
    }

    @Test
    fun givenStringArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()
        val arr = arrayOf("one", "two", "three")

        fragment.stringArrayValue = arr

        val stored = fragment.arguments?.getStringArray("stringArrayValue")
        assertArrayEquals(arr, stored)
        assertArrayEquals(arr, fragment.stringArrayValue)
    }

    @Test
    fun givenStringArrayArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.stringArrayValue
        }
    }

    @Test
    fun givenParcelableArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()
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
    fun givenParcelableArrayArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.parcelableArrayValue
        }
    }

    @Test
    fun givenCharSequenceArrayArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()

        fragment.charSequenceArrayValue = arrayOf("x", "y")

        val stored = fragment.arguments?.getCharSequenceArray("charSequenceArrayValue")
        assertArrayEquals(arrayOf("x", "y"), stored)
        assertArrayEquals(arrayOf("x", "y"), fragment.charSequenceArrayValue)
    }

    @Test
    fun givenCharSequenceArrayArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.charSequenceArrayValue
        }
    }

    @Test
    fun givenStringListArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()
        val listVal = arrayListOf("alpha", "beta")

        fragment.stringListValue = listVal

        val stored = fragment.arguments?.getStringArrayList("stringListValue")
        assertEquals(listOf("alpha", "beta"), stored)
        assertEquals(listOf("alpha", "beta"), fragment.stringListValue)
    }

    @Test
    fun givenStringListArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.stringListValue
        }
    }

    @Test
    fun givenIntListArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()
        val listVal = arrayListOf(100, 200)

        fragment.intListValue = listVal

        val stored = fragment.arguments?.getIntegerArrayList("intListValue")
        assertEquals(listOf(100, 200), stored)
        assertEquals(listOf(100, 200), fragment.intListValue)
    }

    @Test
    fun givenIntListArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.intListValue
        }
    }

    @Test
    fun givenCharSequenceListArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()
        val listVal = arrayListOf("c1", "c2")

        fragment.charSequenceListValue = listVal

        val stored = fragment.arguments?.getCharSequenceArrayList("charSequenceListValue")
        assertEquals(listOf("c1", "c2"), stored)
        assertEquals(listOf("c1", "c2"), fragment.charSequenceListValue)
    }

    @Test
    fun givenCharSequenceListArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.charSequenceListValue
        }
    }

    @Test
    fun givenParcelableListArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()
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
    fun givenParcelableListArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.parcelableListValue
        }
    }

    @Test
    fun givenParcelableArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()

        fragment.parcelableValue = TestParcelable("parcelData", NestedTestParcelable(100L))

        val stored = BundleCompat.getParcelable<TestParcelable>(
            fragment.arguments!!,
            "parcelableValue",
            TestParcelable::class.java
        )
        assertNotNull(stored)
        assertEquals("parcelData", stored.label)
        assertEquals(100L, stored.meta.size)

        val retrieved = fragment.parcelableValue
        assertEquals("parcelData", retrieved.label)
        assertEquals(100L, retrieved.meta.size)
    }

    @Test
    fun givenParcelableArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.parcelableValue
        }
    }

    @Test
    fun givenSerializableArgument_whenSetValue_thenShouldRetrieveSameValue() {
        val fragment = NotNullArgumentFragment()
        fragment.serializableValue = TestSerializable("serialData", NestedTestSerializable(200L))

        val stored = BundleCompat.getSerializable<TestSerializable>(
            fragment.arguments!!,
            "serializableValue",
            TestSerializable::class.java
        )
        assertNotNull(stored)
        assertEquals("serialData", stored.label)
        assertEquals(200L, stored.meta.size)

        val retrieved = fragment.serializableValue
        assertEquals("serialData", retrieved.label)
        assertEquals(200L, retrieved.meta.size)
    }

    @Test
    fun givenSerializableArgument_whenNotSetValue_thenShouldThrowException() {
        val fragment = NotNullArgumentFragment()

        assertFailsWith<IllegalStateException> {
            fragment.serializableValue
        }
    }
}
