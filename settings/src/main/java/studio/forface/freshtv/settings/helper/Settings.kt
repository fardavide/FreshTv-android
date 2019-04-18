package studio.forface.freshtv.settings.helper

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import studio.forface.freshtv.domain.gateways.AppSettings
import studio.forface.freshtv.domain.gateways.SettingsListener
import studio.forface.freshtv.domain.utils.EMPTY_STRING

/**
 * A collection of storage-backed key-value data
 *
 * This class allows storage of values with the [Int], [Long], [String], [Float], [Double], or [Boolean] types, using a
 * [String] reference as a key. Values will be persisted across app launches.
 *
 * Operator extensions are defined in order to simplify usage. In addition, property delegates are provided for cleaner
 * syntax and better type-safety when interacting with values stored in a `Settings` instance.
 *
 *
 * @author Davide Giuseppe Farella.
 */
internal class Settings( private val preferences: SharedPreferences ) {

    /** Clears all values stored in this [Settings] instance */
    fun clear() {
        preferences { preferences.all.forEach { remove( it.key ) } }
    }

    /** Removes the value stored at [key] */
    fun remove( key: String ) = preferences { remove( key ) }

    /** @return `true` if there is a value stored at [key], or `false` otherwise */
    fun hasKey( key: String ) = preferences.contains( key )

    /**
     * @return the `Boolean` value stored at [key], or [defaultValue] if no value was stored. If a value of a different
     * type was stored at `key`, the behavior is not defined.
     */
    fun getBoolean( key: String, defaultValue: Boolean = false ) = preferences.getBoolean( key, defaultValue )

    /**
     * @return the `Double` value stored at [key], or [defaultValue] if no value was stored. If a value of a different
     * type was stored at `key`, the behavior is not defined.
     */
    fun getDouble( key: String, defaultValue: Double = 0.0 ) =
        Double.fromBits( preferences.getLong( key, defaultValue.toRawBits() ) )

    /**
     * @return the `Float` value stored at [key], or [defaultValue] if no value was stored. If a value of a different
     * type was stored at `key`, the behavior is not defined.
     */
    fun getFloat( key: String, defaultValue: Float = 0f ) = preferences.getFloat( key, defaultValue )

    /**
     * @return the `Int` value stored at [key], or [defaultValue] if no value was stored. If a value of a different
     * type was stored at `key`, the behavior is not defined.
     */
    fun getInt( key: String, defaultValue: Int = 0 ) = preferences.getInt( key, defaultValue )

    /**
     * @return the `Long` value stored at [key], or [defaultValue] if no value was stored. If a value of a different
     * type was stored at `key`, the behavior is not defined.
     */
    fun getLong( key: String, defaultValue: Long = 0 ) = preferences.getLong( key, defaultValue )

    /**
     * @return the `String` value stored at [key], or [defaultValue] if no value was stored. If a value of a different
     * type was stored at `key`, the behavior is not defined.
     */
    fun getString( key: String, defaultValue: String = EMPTY_STRING ) =
        preferences.getString( key, defaultValue ) ?: defaultValue

    /** Stores the `Boolean` [value] at [key] */
    fun putBoolean( key: String, value: Boolean ) {
        preferences { putBoolean( key, value ) }
    }

    /** Stores the `Double` [value] at [key] */
    fun putDouble( key: String, value: Double ) {
        preferences { putLong( key, value.toRawBits() ) }
    }

    /** Stores the `Float` [value] at [key] */
    fun putFloat( key: String, value: Float ) {
        preferences { putFloat( key, value ) }
    }

    /** Stores the `Int` [value] at [key] */
    fun putInt( key: String, value: Int ) {
        preferences { putInt( key, value ) }
    }

    /** Stores the `Long` [value] at [key] */
    fun putLong( key: String, value: Long ) {
        preferences { putLong( key, value ) }
    }

    /** Stores the `String` [value] at [key] */
    fun putString( key: String, value: String ) {
        preferences { putString( key, value ) }
    }

    /**
     * Adds a listener which will call the supplied [callback] anytime the value at [key] changes. A [Listener]
     * reference is returned which should be passed to [removeListener] when you no longer need it so that the
     * associated platform resources can be cleaned up.
     *
     * A strong reference should be held to the `Listener` returned by this method in order to avoid it being
     * garbage-collected on Android.
     *
     * No attempt is made in the current implementation to safely handle multithreaded interaction with the listener, so
     * it's recommended that interaction with the listener APIs be confined to the main UI thread.
     *
     * @return [Settings.Listener]
     */
    fun <T> addListener( key: String, callback: (T) -> Unit ): SettingsListener {
        val cache = Listener.Cache( preferences.all[key] )

        val prefsListener =
            SharedPreferences.OnSharedPreferenceChangeListener { _: SharedPreferences, updatedKey: String ->
                if ( updatedKey != key ) return@OnSharedPreferenceChangeListener

                /*
                 According to the OnSharedPreferenceChangeListener contract, we might get called for an update even
                 if the value didn't change. We hold a cache to ensure that the user-supplied callback only updates on
                 actual changes.
                 */
                val prev = cache.value
                val current = preferences.all[key]
                if ( prev != current ) {
                    @Suppress("UNCHECKED_CAST")
                    callback( current as T )
                    cache.value = current
                }
            }
        preferences.registerOnSharedPreferenceChangeListener( prefsListener )
        return Listener( prefsListener )
    }

    /** Unsubscribes the [listener] from receiving updates to the value at the key it monitors */
    fun removeListener( listener: SettingsListener ) {
        listener as Listener
        preferences.unregisterOnSharedPreferenceChangeListener( listener.delegate )
    }

    /**
     * A handle to a listener instance created in [addListener] so it can be passed to [removeListener], wrapper around
     * [SharedPreferences.OnSharedPreferenceChangeListener].
     *
     * Inherit from [AppSettings.Listener]
     */
    class Listener internal constructor(
        delegate: SharedPreferences.OnSharedPreferenceChangeListener
    ) : AppSettings.Listener<SharedPreferences.OnSharedPreferenceChangeListener>( delegate ) {
        internal class Cache( var value: Any? )
    }

    /**
     * Execute a lambda with [SharedPreferences.Editor] of the given [SharedPreferences].
     * @param action is the action to be executed in the [Editor]
     */
    private inline operator fun SharedPreferences.invoke( action: Editor.() -> Unit ) {
        edit().apply { action( this ) }.apply()
    }
}