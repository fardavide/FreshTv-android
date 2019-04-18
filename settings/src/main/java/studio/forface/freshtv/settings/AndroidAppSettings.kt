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
     * An OPTIONAL [String] representing the name of the last selected `MovieChannel`s `Group`
     * Default is `null`
     */
    override var lastMovieChannelGroupName by settings<String?>()

    /**
     * An OPTIONAL [String] representing the id of the last `MovieChannel` shown in the center of the screen
     * Default is `null`
     */
    override var lastMovieChannelId by settings<String?>()

    /**
     * An OPTIONAL [Int] representing the position of the last `MovieChannel` shown on the screen
     * Default is `null`
     */
    override var lastMovieChannelPosition by settings<Int?>()

    /**
     * An OPTIONAL [String] representing the name of the last selected `TvChannel`s `Group`
     * Default is `null`
     */
    override var lastTvChannelGroupName by settings<String?>()

    /**
     * An OPTIONAL [String] representing the id of the last `TvChannel` shown in the center of the screen
     * Default is `null`
     */
    override var lastTvChannelId by settings<String?>()

    /**
     * An OPTIONAL [Int] representing the position of the last `TvChannel` shown on screen
     * Default is `null`
     */
    override var lastTvChannelPosition by settings<Int?>()

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
    override fun <T : Any> addListener( property: KProperty<T>, block: (T) -> Unit ) =
        settings.addListener( property.name, block )

    /** Remove the given [SettingsListener] */
    override fun removeListener( listener: SettingsListener ) {
        settings.removeListener( listener )
    }
}