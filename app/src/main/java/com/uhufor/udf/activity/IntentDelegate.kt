package com.uhufor.udf.activity

import android.app.Activity
import android.os.Bundle
import com.uhufor.udf.bundle.getValueByType
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

class IntentExtra<T : Any>(
    private val key: String? = null,
    private val defaultValue: T? = null,
    private val clazz: KClass<T>,
) : ReadOnlyProperty<Activity, T?> {

    override fun getValue(thisRef: Activity, property: KProperty<*>): T? {
        val extraKey = key ?: property.name
        val extras: Bundle = thisRef.intent?.extras
            ?: throw IllegalStateException("Intent extras are null for key \"$extraKey\"")

        return extras.getValueByType(extraKey, clazz) ?: defaultValue
    }
}

class IntentExtraNonNull<T : Any>(
    private val key: String? = null,
    private val defaultValue: T? = null,
    private val clazz: KClass<T>,
) : ReadOnlyProperty<Activity, T> {

    override fun getValue(thisRef: Activity, property: KProperty<*>): T {
        val extraKey = key ?: property.name
        val extras: Bundle = thisRef.intent?.extras
            ?: throw IllegalStateException("Intent extras are null for key \"$extraKey\"")

        return extras.getValueByType(extraKey, clazz)
            ?: defaultValue
            ?: throw IllegalStateException(
                "Intent extra \"$extraKey\" is missing for non-null property \"${property.name}\""
            )
    }
}

inline fun <reified T : Any> Activity.intentExtra(
    key: String? = null,
    defaultValue: T? = null,
): ReadOnlyProperty<Activity, T?> = IntentExtra(key, defaultValue, T::class)

inline fun <reified T : Any> Activity.intentNotNullExtra(
    key: String? = null,
    defaultValue: T? = null,
): ReadOnlyProperty<Activity, T> = IntentExtraNonNull(key, defaultValue, T::class)
