package com.uhufor.udf.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.uhufor.udf.bundle.getValueByType
import com.uhufor.udf.bundle.putValueByType
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/**
 * [ArgumentDelegate]는 Fragment의 arguments Bundle에 타입 [T]의 nullable 프로퍼티를 간편하게
 * 저장/읽을 수 있도록 돕는 Delegate입니다.
 *
 * @param defaultValue 만약 arguments Bundle에 값이 존재하지 않을 경우 반환할 기본값 (nullable)
 * @constructor Bundle 내에 값이 없을 때 [defaultValue]를 반환하는 Delegate를 생성합니다.
 */
class ArgumentDelegate<T : Any>(
    private val clazz: KClass<T>,
    private val defaultValue: T? = null,
) : ReadWriteProperty<Fragment, T?> {

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T? {
        val key = property.name
        val arguments = thisRef.arguments ?: return defaultValue
        return arguments.getValueByType(key, clazz) ?: defaultValue
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

/**
 * [ArgumentNotNullDelegate]는 Fragment의 arguments Bundle에 타입 [T]의 non-null 프로퍼티를 간편하게
 * 저장/읽을 수 있도록 돕는 Delegate입니다.
 *
 * arguments Bundle 또는 해당 key의 값이 존재하지 않으면 [IllegalStateException]을 던집니다.
 */
class ArgumentNotNullDelegate<T : Any>(
    private val clazz: KClass<T>,
) : ReadWriteProperty<Fragment, T> {

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val key = property.name
        val arguments = thisRef.arguments
            ?: throw IllegalStateException(
                "Arguments Bundle is null for property '${property.name}'."
            )

        val value = arguments.getValueByType<T>(key, clazz)
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

inline fun <reified T : Any> Fragment.argument(
    defaultValue: T? = null,
): ReadWriteProperty<Fragment, T?> =
    ArgumentDelegate(clazz = T::class, defaultValue = defaultValue)

inline fun <reified T : Any> Fragment.argumentNotNull(): ReadWriteProperty<Fragment, T> =
    ArgumentNotNullDelegate(clazz = T::class)
