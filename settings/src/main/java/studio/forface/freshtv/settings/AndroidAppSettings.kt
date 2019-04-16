package studio.forface.freshtv.settings

import studio.forface.freshtv.domain.gateways.AppSettings
import studio.forface.freshtv.domain.gateways.SettingsListener
import studio.forface.freshtv.settings.helper.Settings
import studio.forface.freshtv.settings.helper.invoke
import kotlin.reflect.KProperty

/**
 * @author Davide Giuseppe Farella
 * Android implementation of [AppSettings]
 */
internal class AndroidAppSettings( private val settings: Settings ): AppSettings {

    /**
     * A [Boolean] representing whether the night mode is enabled
     * Default is `true`
     */
    override var nightMode by settings(true )

    /**
     * A [Long] representing the days before an old Guide will be deleted
     * Default is `3`
     */
    override var oldGuidesLifespanDays by settings(3L )

    /**
     * Add a listener for the given [KProperty]
     * @return [SettingsListener]
     */
    override fun <T> addListener( property: KProperty<T>, block: (T) -> Unit ) =
        settings.addListener( property.name, block )

    /** Remove the given [SettingsListener] */
    override fun removeListener( listener: SettingsListener ) {
        settings.removeListener( listener )
    }
}