package studio.forface.freshtv.settings

import studio.forface.freshtv.domain.gateways.AppSettings
import studio.forface.freshtv.settings.helper.Settings
import studio.forface.freshtv.settings.helper.invoke

/**
 * @author Davide Giuseppe Farella
 * Android implementation of [AppSettings]
 */
internal class AndroidAppSettings( settings: Settings ): AppSettings {

    /**
     * A [Boolean] representing whether the night mode is enabled
     * Default is `true`
     */
    override var nightMode by settings<Boolean>(true )

    /**
     * The day before an old Guide will be deleted
     * Default is `3`
     */
    override var oldGuidesLifespanDays by settings<Long>(3 )
}