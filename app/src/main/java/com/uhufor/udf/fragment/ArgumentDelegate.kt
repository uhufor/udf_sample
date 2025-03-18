package com.uhufor.udf.fragment

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ArgumentDelegate<T : Any>(
    private val defaultValue: T? = null,
) : ReadWriteProperty<Fragment, T?> {

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T? {
        val key = property.name
        val arguments = thisRef.arguments ?: return defaultValue
        return arguments.getValueByType(key) ?: defaultValue
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T?) {
        if (thisRef.arguments == null) {
            thisRef.arguments = Bundle()
        }
        val key = property.name
        if (value == null) {
            thisRef.arguments?.remove(key)
        } else {
            thisRef.arguments?.putValueByType(key, value)
        }
    }
}

class ArgumentNotNullDelegate<T : Any> : ReadWriteProperty<Fragment, T> {

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val key = property.name
        val arguments = thisRef.arguments
            ?: throw IllegalStateException(
                "Arguments Bundle is null for property '${property.name}'."
            )

        val value = arguments.getValueByType<T>(key)
        return value
            ?: throw IllegalStateException(
                "Property '${property.name}' could not be read from the Fragment arguments."
            )
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        if (thisRef.arguments == null) {
            thisRef.arguments = Bundle()
        }
        val key = property.name
        thisRef.arguments?.putValueByType(key, value)
    }
}

@Suppress("UNCHECKED_CAST", "DEPRECATION")
private fun <T : Any> Bundle.getValueByType(key: String): T? {
    if (!containsKey(key)) return null
    val rawValue = get(key) ?: return null

    return when (rawValue) {
        is Boolean -> rawValue as T
        is Byte -> rawValue as T
        is Char -> rawValue as T
        is Short -> rawValue as T
        is Int -> rawValue as T
        is Long -> rawValue as T
        is Float -> rawValue as T
        is Double -> rawValue as T
        is String -> rawValue as T
        is CharSequence -> rawValue as T
        is BooleanArray -> rawValue as T
        is ByteArray -> rawValue as T
        is ShortArray -> rawValue as T
        is CharArray -> rawValue as T
        is IntArray -> rawValue as T
        is LongArray -> rawValue as T
        is FloatArray -> rawValue as T
        is DoubleArray -> rawValue as T
        is Array<*> -> rawValue as? T
        is List<*> -> rawValue as? T
        is Parcelable -> rawValue as? T
        is Serializable -> rawValue as? T
        else -> null
    }
}

@Suppress("UNCHECKED_CAST")
private fun <T : Any> Bundle.putValueByType(key: String, value: T) {
    when (value) {
        is Boolean -> putBoolean(key, value)
        is Byte -> putByte(key, value)
        is Char -> putChar(key, value)
        is Short -> putShort(key, value)
        is Int -> putInt(key, value)
        is Long -> putLong(key, value)
        is Float -> putFloat(key, value)
        is Double -> putDouble(key, value)
        is String -> putString(key, value)
        is CharSequence -> putCharSequence(key, value)
        is BooleanArray -> putBooleanArray(key, value)
        is ByteArray -> putByteArray(key, value)
        is ShortArray -> putShortArray(key, value)
        is CharArray -> putCharArray(key, value)
        is IntArray -> putIntArray(key, value)
        is LongArray -> putLongArray(key, value)
        is FloatArray -> putFloatArray(key, value)
        is DoubleArray -> putDoubleArray(key, value)
        is Array<*> -> {
            when {
                value.isArrayOf<String>() -> {
                    putStringArray(key, value as Array<String>)
                }

                value.isArrayOf<Parcelable>() -> {
                    putParcelableArray(key, value as Array<Parcelable>)
                }

                value.isArrayOf<CharSequence>() -> {
                    putCharSequenceArray(key, value as Array<CharSequence>)
                }

                else -> {
                    throw IllegalArgumentException("Unsupported array type: $value")
                }
            }
        }

        is List<*> -> {
            when {
                value.all { it is String } -> {
                    putStringArrayList(key, value as ArrayList<String>)
                }

                value.all { it is Int } -> {
                    putIntegerArrayList(key, value as ArrayList<Int>)
                }

                value.all { it is CharSequence } -> {
                    putCharSequenceArrayList(key, value as ArrayList<CharSequence>)
                }

                value.all { it is Parcelable } -> {
                    putParcelableArrayList(key, value as ArrayList<Parcelable>)
                }

                else -> {
                    throw IllegalArgumentException("Unsupported list type: $value")
                }
            }
        }

        is Parcelable -> putParcelable(key, value)
        is Serializable -> putSerializable(key, value)
        else -> throw IllegalArgumentException("Unsupported type: $value")
    }
}

fun <T : Any> Fragment.argument(defaultValue: T? = null): ReadWriteProperty<Fragment, T?> =
    ArgumentDelegate(defaultValue)

fun <T : Any> Fragment.argumentNotNull(): ReadWriteProperty<Fragment, T> =
    ArgumentNotNullDelegate()
