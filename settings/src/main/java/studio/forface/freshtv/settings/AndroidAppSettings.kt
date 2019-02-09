package studio.forface.freshtv.settings

import studio.forface.freshtv.domain.gateways.AppSettings
import studio.forface.freshtv.settings.helper.Settings
import studio.forface.freshtv.settings.helper.invoke

/**
 * @author Davide Giuseppe Farella
 * Android implementation of [AppSettings]
 */
internal class AndroidAppSettings( settings: Settings): AppSettings {

    /** @see AppSettings.oldGuidesLifespanDays */
    override var oldGuidesLifespanDays by settings<Long>(3 )
}