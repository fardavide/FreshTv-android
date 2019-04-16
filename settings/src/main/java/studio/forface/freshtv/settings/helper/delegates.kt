package studio.forface.freshtv.settings.helper

import studio.forface.freshtv.domain.gateways.AppSettings
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @return an [Int] property delegate, backed by this [Settings] instance using the provided [key], with initial value
 * [defaultValue]
 */
internal inline operator fun <reified V: Any> Settings.invoke(
    defaultValue: V,
    key: String? = null
): OptKeyDelegate<V> = SettingsDelegate( key, defaultValue , getter(), setter() )

/**
 * Returns a nullable [V] property delegate, backed by this [Settings] instance using the provided [key], with initial
 * value `null`
 * @throws IllegalArgumentException if [V] is not nullable.
 */
internal inline operator fun <reified V: Any?> Settings.invoke( key: String? = null ): ReadWriteProperty<Any?, V?> {
    if ( null !is V /* T is not nullable */ )
        throw IllegalArgumentException(
            "A default value must be declared if return type `${V::class.qualifiedName}` is not nullable"
        )
    @Suppress("RemoveExplicitTypeArguments")
    return OptSettingsDelegate<V>( key , getter(), setter() )
}

internal inline fun <reified V: Any?> Settings.getter(): (String) -> V? = { key -> get<V>( key ) }
internal inline fun <reified V: Any?> Settings.setter(): (String, V) -> Unit = { key, value -> set( key, value ) }

/**
 * An [OptKeyDelegate] for a NOT NULLABLE [V] value of [Settings]. Injected [getter] and [setter] are required because
 * class is not inlined, so it can't hold a *reified* [V], which a function can.
 */
internal class SettingsDelegate<V: Any>(
    key: String?,
    private val default: V,
    private val getter: (String) -> V?,
    private val setter: (String, V) -> Unit
): OptKeyDelegate<V>( key ) {

    override fun getValue( key: String ): V = getter( key ) ?: default
    override fun setValue( key: String, value: V ) {
        setter( key, value )
    }
}

/**
 * An [OptKeyDelegate] for a NULLABLE [V] value of [Settings]. Injected [getter] and [setter] are required because
 * class is not inlined, so it can't hold a *reified* [V], which a function can.
 */
internal open class OptSettingsDelegate<V: Any?>(
    key: String?,
    private val getter: (String) -> V?,
    private val setter: (String, V?) -> Unit
): OptKeyDelegate<V?>( key ) {

    override fun getValue( key: String ): V? = getter( key )
    override fun setValue( key: String, value: V? ) {
        setter( key, value )
    }
}

/**
 * A [ReadWriteProperty] that `get` and `set` through a [String] key.
 * @param _key an OPTIONAl key [String], if this value is null, the name [KProperty.name] will be used as key.
 */
internal abstract class OptKeyDelegate<T: Any?>( _key: String? ): ReadWriteProperty<Any?, T> {
    private var finalKey: String? = _key
    private val KProperty<*>.key: String get() {
        if ( finalKey == null ) finalKey = name
        return finalKey!!
    }

    abstract fun getValue( key: String ): T
    abstract fun setValue( key: String, value: T )

    override fun getValue( thisRef: Any?, property: KProperty<*> ): T = getValue( property.key )
    override fun setValue( thisRef: Any?, property: KProperty<*>, value: T ) {
        setValue( property.key, value )
    }
}