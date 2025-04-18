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
    var boolValue by argumentSuper<Boolean?>()
    var byteValue by argumentSuper<Byte?>()
    var charValue by argumentSuper<Char?>()
    var shortValue by argumentSuper<Short?>()
    var intValue by argumentSuper<Int?>()
    var longValue by argumentSuper<Long?>()
    var floatValue by argumentSuper<Float?>()
    var doubleValue by argumentSuper<Double?>()
    var stringValue by argumentSuper<String?>()
    var charSequenceValue by argumentSuper<CharSequence?>()

    // Primitive Array
    var boolArrayValue by argumentSuper<BooleanArray?>()
    var byteArrayValue by argumentSuper<ByteArray?>()
    var shortArrayValue by argumentSuper<ShortArray?>()
    var charArrayValue by argumentSuper<CharArray?>()
    var intArrayValue by argumentSuper<IntArray?>()
    var longArrayValue by argumentSuper<LongArray?>()
    var floatArrayValue by argumentSuper<FloatArray?>()
    var doubleArrayValue by argumentSuper<DoubleArray?>()

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
    var parcelableValue by argumentSuper<TestParcelable?>()
    var serializableValue by argumentSuper<TestSerializable?>()
}

internal class NullableArgumentWithDefaultFragment : Fragment() {
    // Primitive with default value
    var boolValueWithDefault by argumentSuper<Boolean?>(default = DEFAULT_BOOL_VALUE)
    var intValueWithDefault by argumentSuper<Int?>(default = DEFAULT_INT_VALUE)

    companion object {
        const val DEFAULT_BOOL_VALUE = true
        const val DEFAULT_INT_VALUE = 500
    }
}

internal class NotNullArgumentFragment : Fragment() {
    // Primitive
    var boolValue by argumentSuper<Boolean>()
    var byteValue by argumentSuper<Byte>()
    var charValue by argumentSuper<Char>()
    var shortValue by argumentSuper<Short>()
    var intValue by argumentSuper<Int>()
    var longValue by argumentSuper<Long>()
    var floatValue by argumentSuper<Float>()
    var doubleValue by argumentSuper<Double>()
    var stringValue by argumentSuper<String>()
    var charSequenceValue by argumentSuper<CharSequence>()

    // Primitive Array
    var boolArrayValue by argumentSuper<BooleanArray>()
    var byteArrayValue by argumentSuper<ByteArray>()
    var shortArrayValue by argumentSuper<ShortArray>()
    var charArrayValue by argumentSuper<CharArray>()
    var intArrayValue by argumentSuper<IntArray>()
    var longArrayValue by argumentSuper<LongArray>()
    var floatArrayValue by argumentSuper<FloatArray>()
    var doubleArrayValue by argumentSuper<DoubleArray>()

//    // Typed Array
//    var stringArrayValue by argumentNotNull<Array<String>>()
//    var parcelableArrayValue by argumentNotNull<Array<TestParcelable>>()
//    var charSequenceArrayValue by argumentNotNull<Array<CharSequence>>()
//
//    // Typed ArrayList
//    var stringListValue by argumentNotNull<List<String>>()
//    var intListValue by argumentNotNull<List<Int>>()
//    var charSequenceListValue by argumentNotNull<List<CharSequence>>()
//    var parcelableListValue by argumentNotNull<List<TestParcelable>>()

    // Typed Class
    var parcelableValue by argumentSuper<TestParcelable>()
    var serializableValue by argumentSuper<TestSerializable>()
}

internal class NotNullArgumentWithDefaultFragment : Fragment() {
    // Primitive with default value
    var boolValueWithDefault by argumentSuper<Boolean>(default = DEFAULT_BOOL_VALUE)
    var intValueWithDefault by argumentSuper<Int>(default = DEFAULT_INT_VALUE)

    companion object {
        const val DEFAULT_BOOL_VALUE = false
        const val DEFAULT_INT_VALUE = -1
    }
}

