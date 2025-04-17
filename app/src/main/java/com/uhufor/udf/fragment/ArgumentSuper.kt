package com.uhufor.udf.fragment

import android.os.Binder
import android.os.Bundle
import android.os.Parcelable
import android.util.Size
import android.util.SizeF
import androidx.core.os.BundleCompat
import androidx.fragment.app.Fragment
import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.reflect.typeOf

inline fun <reified T> Fragment.argumentSuper(
    defaultValue: T? = null,
): ReadWriteProperty<Fragment, T> {

    val isNullable = typeOf<T>().isMarkedNullable
    println("argumentSuper[${T::class.simpleName}]: $isNullable")

    return if (isNullable) {
        argumentSuperNullable(defaultValue)
    } else {
        argumentSuperNotNull(defaultValue)
    }
}

inline fun <reified T> Fragment.argumentSuperNullable(
    defaultValue: T? = null,
): ReadWriteProperty<Fragment, T> =
    object : ReadWriteProperty<Fragment, T> {

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
            val key = property.name
            val arguments = thisRef.arguments ?: return defaultValue as T

            return (arguments.getValueBySuper(key) ?: defaultValue) as T
        }

        override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
            val key = property.name
            val args = thisRef.arguments ?: Bundle().also(thisRef::setArguments)

            if (value == null) {
                args.remove(key)
            } else {
                args.putValueBySuper(key, value)
            }
        }
    }

inline fun <reified T> Fragment.argumentSuperNotNull(
    defaultValue: T? = null,
): ReadWriteProperty<Fragment, T> =
    object : ReadWriteProperty<Fragment, T> {

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
            val key = property.name
            val value = thisRef.arguments
                ?.getValueBySuper(key)
                ?: defaultValue

            check(value != null) {
                "Property '${property.name}' could not be read from the Fragment arguments."
            }

            return value
        }

        override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
            val key = property.name
            val args = thisRef.arguments ?: Bundle().also(thisRef::setArguments)

            args.putValueBySuper(key, value)
        }
    }

inline fun <reified T> Bundle.getValueBySuper(key: String): T? {
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
        Binder::class -> getBinder(key)
        Bundle::class -> getBundle(key)
        Size::class -> getSize(key)
        SizeF::class -> getSizeF(key)
        else -> {
            if (Parcelable::class.java.isAssignableFrom(T::class.java)) {
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

inline fun <reified T> Bundle.putValueBySuper(key: String, value: T) {
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
        is Binder -> putBinder(key, value)
        is Bundle -> putBundle(key, value)
        is Size -> putSize(key, value)
        is SizeF -> putSizeF(key, value)
        is Parcelable -> putParcelable(key, value)
        is Serializable -> putSerializable(key, value)
        else -> throw IllegalArgumentException("Unsupported type: $value")
    }
}
