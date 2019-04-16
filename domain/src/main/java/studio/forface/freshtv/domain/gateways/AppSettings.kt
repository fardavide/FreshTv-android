package studio.forface.freshtv.domain.gateways

import kotlin.reflect.KProperty

/**
 * @author Davide Giuseppe Farella
 * A class for manage app's Settings
 */
interface AppSettings {

    /** A [Boolean] representing whether the night mode is enabled */
    var nightMode: Boolean

    /** The day before an old Guide will be deleted */
    var oldGuidesLifespanDays: Long

    /**
     * Add a listener for the given [KProperty]
     * @return [SettingsListener]
     */
    fun <T> addListener( property: KProperty<T>, block: (T) -> Unit ) : SettingsListener

    /** Remove the given [SettingsListener] */
    fun removeListener( listener: SettingsListener )

    // TODO enum class UiMode { Light, Dark, System, Battery, Schedule }

    /**
     * A listener for the [AppSettings]
     * @property delegate the platform specific listener
     */
    abstract class Listener<D>( val delegate: D )
}

/** A typealias for generic [AppSettings.Listener] */
typealias SettingsListener = AppSettings.Listener<*>