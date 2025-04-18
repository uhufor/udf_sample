package com.uhufor.udf.fragment

import android.os.Parcelable
import androidx.fragment.app.Fragment
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
internal class TestParcelable(val label: String, val meta: NestedTestParcelable) : Parcelable

@Parcelize
internal class NestedTestParcelable(val size: Long) : Parcelable

internal class TestSerializable(val label: String, val meta: NestedTestSerializable) : Serializable

internal class NestedTestSerializable(val size: Long) : Serializable

internal class NullableArgumentFragment : Fragment() {
    // Nullable and has default value will be conflicted
    // Primitive
    var boolValue by argument<Boolean?>()
    var byteValue by argument<Byte?>()
    var charValue by argument<Char?>()
    var shortValue by argument<Short?>()
    var intValue by argument<Int?>()
    var longValue by argument<Long?>()
    var floatValue by argument<Float?>()
    var doubleValue by argument<Double?>()
    var stringValue by argument<String?>()
    var charSequenceValue by argument<CharSequence?>()

    // Primitive Array
    var boolArrayValue by argument<BooleanArray?>()
    var byteArrayValue by argument<ByteArray?>()
    var shortArrayValue by argument<ShortArray?>()
    var charArrayValue by argument<CharArray?>()
    var intArrayValue by argument<IntArray?>()
    var longArrayValue by argument<LongArray?>()
    var floatArrayValue by argument<FloatArray?>()
    var doubleArrayValue by argument<DoubleArray?>()

//    // Typed Array
//    var stringArrayValue by argument<Array<String>>()
//    var parcelableArrayValue by argument<Array<TestParcelable>>()
//    var charSequenceArrayValue by argument<Array<CharSequence>>()
//
//    // Typed ArrayList
//    var stringListValue by argument<List<String>>()
//    var intListValue by argument<List<Int>>()
//    var charSequenceListValue by argument<List<CharSequence>>()
//    var parcelableListValue by argument<List<TestParcelable>>()

    // Typed Class
    var parcelableValue by argument<TestParcelable?>()
    var serializableValue by argument<TestSerializable?>()
}

internal class NullableArgumentWithDefaultFragment : Fragment() {
    // Primitive with default value
    var boolValueWithDefault by argument<Boolean?>(default = DEFAULT_BOOL_VALUE)
    var intValueWithDefault by argument<Int?>(default = DEFAULT_INT_VALUE)

    companion object {
        const val DEFAULT_BOOL_VALUE = true
        const val DEFAULT_INT_VALUE = 500
    }
}

internal class NotNullArgumentFragment : Fragment() {
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

//    // Typed Array
//    var stringArrayValue by argument<Array<String>>()
//    var parcelableArrayValue by argument<Array<TestParcelable>>()
//    var charSequenceArrayValue by argument<Array<CharSequence>>()
//
//    // Typed ArrayList
//    var stringListValue by argument<List<String>>()
//    var intListValue by argument<List<Int>>()
//    var charSequenceListValue by argument<List<CharSequence>>()
//    var parcelableListValue by argument<List<TestParcelable>>()

    // Typed Class
    var parcelableValue by argument<TestParcelable>()
    var serializableValue by argument<TestSerializable>()
}

internal class NotNullArgumentWithDefaultFragment : Fragment() {
    // Primitive with default value
    var boolValueWithDefault by argument<Boolean>(default = DEFAULT_BOOL_VALUE)
    var intValueWithDefault by argument<Int>(default = DEFAULT_INT_VALUE)

    companion object {
        const val DEFAULT_BOOL_VALUE = false
        const val DEFAULT_INT_VALUE = -1
    }
}

