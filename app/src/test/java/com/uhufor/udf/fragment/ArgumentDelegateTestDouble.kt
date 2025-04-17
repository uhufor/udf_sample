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
    var parcelableValue by argument<TestParcelable>()
    var serializableValue by argument< TestSerializable>()
}

internal class NotNullArgumentFragment : Fragment() {
    // Primitive
    var boolValue by argumentNotNull<Boolean>()
    var byteValue by argumentNotNull<Byte>()
    var charValue by argumentNotNull<Char>()
    var shortValue by argumentNotNull<Short>()
    var intValue by argumentNotNull<Int>()
    var longValue by argumentNotNull<Long>()
    var floatValue by argumentNotNull<Float>()
    var doubleValue by argumentNotNull<Double>()
    var stringValue by argumentNotNull<String>()
    var charSequenceValue by argumentNotNull<CharSequence>()

    // Primitive Array
    var boolArrayValue by argumentNotNull<BooleanArray>()
    var byteArrayValue by argumentNotNull<ByteArray>()
    var shortArrayValue by argumentNotNull<ShortArray>()
    var charArrayValue by argumentNotNull<CharArray>()
    var intArrayValue by argumentNotNull<IntArray>()
    var longArrayValue by argumentNotNull<LongArray>()
    var floatArrayValue by argumentNotNull<FloatArray>()
    var doubleArrayValue by argumentNotNull<DoubleArray>()

    // Typed Array
    var stringArrayValue by argumentNotNull<Array<String>>()
    var parcelableArrayValue by argumentNotNull<Array<TestParcelable>>()
    var charSequenceArrayValue by argumentNotNull<Array<CharSequence>>()

    // Typed ArrayList
    var stringListValue by argumentNotNull<List<String>>()
    var intListValue by argumentNotNull<List<Int>>()
    var charSequenceListValue by argumentNotNull<List<CharSequence>>()
    var parcelableListValue by argumentNotNull<List<TestParcelable>>()

    // Typed Class
    var parcelableValue by argumentNotNull<TestParcelable>()
    var serializableValue by argumentNotNull<TestSerializable>()
}


