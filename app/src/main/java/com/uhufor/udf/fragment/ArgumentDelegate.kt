package com.uhufor.udf.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.uhufor.udf.bundle.getValue
import com.uhufor.udf.bundle.putValue
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.reflect.typeOf

inline fun <reified T> Fragment.argument(
    default: T? = null,
): ReadWriteProperty<Fragment, T> =
    if (typeOf<T>().isMarkedNullable) argumentNullable(default) else argumentNotNull(default)

inline fun <reified T> Fragment.argumentNullable(
    default: T? = null,
): ReadWriteProperty<Fragment, T> = object : ReadWriteProperty<Fragment, T> {

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val key = property.name
        val arguments = thisRef.arguments ?: return default as T

        return (arguments.getValue(key) ?: default) as T
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        val key = property.name
        val args = thisRef.arguments ?: Bundle().also(thisRef::setArguments)

        if (value == null) {
            args.remove(key)
        } else {
            args.putValue(key, value)
        }
    }
}

inline fun <reified T> Fragment.argumentNotNull(
    default: T? = null,
): ReadWriteProperty<Fragment, T> = object : ReadWriteProperty<Fragment, T> {

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val key = property.name
        val value = thisRef.arguments
            ?.getValue(key)
            ?: default

        check(value != null) {
            "Property '${property.name}' could not be read from the Fragment arguments."
        }

        return value
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        val key = property.name
        val args = thisRef.arguments ?: Bundle().also(thisRef::setArguments)

        args.putValue(key, value)
    }
}
