package com.uhufor.udf.bundle

import android.os.Binder
import android.os.Bundle
import android.os.Parcelable
import android.util.Size
import android.util.SizeF
import androidx.core.os.BundleCompat
import java.io.Serializable
import kotlin.reflect.KClass

inline fun <reified T> Bundle.getValue(key: String): T? {
    if (!containsKey(key)) return null

    return when (T::class) {
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
        Bundle::class -> getBundle(key)
        Size::class -> getSize(key)
        SizeF::class -> getSizeF(key)
        else -> {
            if (Binder::class.java.isAssignableFrom(T::class.java)) {
                getBinder(key)
            } else if (Parcelable::class.java.isAssignableFrom(T::class.java)) {
                BundleCompat.getParcelable(this, key, T::class.java)
            } else if (Serializable::class.java.isAssignableFrom(T::class.java)) {
                @Suppress("UNCHECKED_CAST")
                BundleCompat.getSerializable(this, key, T::class.java as Class<Serializable>)
            } else {
                throw IllegalArgumentException()
            }
        }
    } as? T
}

inline fun <reified T> Bundle.putValue(key: String, value: T) {
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
        is Bundle -> putBundle(key, value)
        is Size -> putSize(key, value)
        is SizeF -> putSizeF(key, value)
        is Binder -> putBinder(key, value)
        is Parcelable -> putParcelable(key, value)
        is Serializable -> putSerializable(key, value)
        else -> throw IllegalArgumentException("Unsupported type: $value")
    }
}

// Old -------------------------------------------------------------------------------
@Suppress("UNCHECKED_CAST")
fun <T : Any> Bundle.getValueByType(key: String, clazz: KClass<T>): T? {
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
        else -> {
            if (Parcelable::class.java.isAssignableFrom(clazz.java)) {
                BundleCompat.getParcelable(this, key, clazz.java)
            } else if (Serializable::class.java.isAssignableFrom(clazz.java)) {
                BundleCompat.getSerializable(
                    this,
                    key,
                    clazz.java as Class<Serializable>
                )
            } else {
                throw IllegalArgumentException()
            }
        }
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

