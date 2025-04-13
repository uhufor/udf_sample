package com.uhufor.udf.bundle

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.BundleCompat
import java.io.Serializable
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
internal fun <T : Any> Bundle.getValueByType(key: String, clazz: KClass<T>): T? {
    if (!containsKey(key)) return null

    return when (clazz) {
        Char::class -> getChar(key)
        CharSequence::class -> getCharSequence(key)
        String::class -> getString(key)
        Boolean::class -> getBoolean(key)
        Byte::class -> getByte(key)
        Short::class -> getShort(key)
        Int::class -> getInt(key)
        Long::class -> getLong(key)
        Float::class -> getFloat(key)
        Double::class -> getDouble(key)
        BooleanArray::class -> getBooleanArray(key)
        ByteArray::class -> getByteArray(key)
        ShortArray::class -> getShortArray(key)
        CharArray::class -> getCharArray(key)
        IntArray::class -> getIntArray(key)
        LongArray::class -> getLongArray(key)
        FloatArray::class -> getFloatArray(key)
        DoubleArray::class -> getDoubleArray(key)
        Parcelable::class -> BundleCompat.getParcelable(this, key, clazz.java)
        Serializable::class -> {
            BundleCompat.getSerializable(
                this,
                key,
                clazz.java as Class<Serializable>
            )
        }
        else -> get(key)
    } as? T
}

@Suppress("UNCHECKED_CAST")
private fun <T : Any> Bundle.getArrayTypeByValue(key: String, clazz: KClass<T>): T? {
    if (!clazz.java.isArray) return null

    val type = clazz.java.componentType ?: return null
    return when {
        type == String::class.java -> getStringArray(key)
        type == CharSequence::class.java -> getCharSequenceArray(key)
        Parcelable::class.java.isAssignableFrom(type) -> {
            BundleCompat.getParcelableArray(this, key, type as Class<Parcelable>)
        }

        else -> null
    } as? T
}

@Suppress("UNCHECKED_CAST")
private fun <T : Any> Bundle.getListTypeByValue(key: String, clazz: KClass<T>): T? {
    if (clazz != List::class) return null

    return get(key) as? T
}

internal fun <T : Any> Bundle.putValueByType(key: String, value: T) {
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
        is Array<*> -> putArrayTypeValue(key, value)
        is List<*> -> putListTypeValue(key, value)
        is Parcelable -> putParcelable(key, value)
        is Serializable -> putSerializable(key, value)
        else -> throw IllegalArgumentException("Unsupported type: $value")
    }
}

@Suppress("UNCHECKED_CAST")
private fun Bundle.putArrayTypeValue(key: String, value: Array<*>) = when {
    value.isArrayOf<String>() -> {
        putStringArray(key, value as Array<String>)
    }

    value.isArrayOf<Parcelable>() -> {
        putParcelableArray(key, value as Array<Parcelable>)
    }

    value.isArrayOf<CharSequence>() -> {
        putCharSequenceArray(key, value as Array<CharSequence>)
    }

    else -> throw IllegalArgumentException("Unsupported array type: $value")
}

@Suppress("UNCHECKED_CAST")
private fun Bundle.putListTypeValue(key: String, value: List<*>) = when {
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

    else -> throw IllegalArgumentException("Unsupported list type: $value")
}

