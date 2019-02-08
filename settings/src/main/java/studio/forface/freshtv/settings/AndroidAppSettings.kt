package studio.forface.freshtv.settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.int
import studio.forface.freshtv.domain.gateways.AppSettings

/**
 * @author Davide Giuseppe Farella
 * Android implementation of [AppSettings]
 */
class AndroidAppSettings( settings: Settings ): AppSettings {

    /** @see AppSettings.oldGuidesLifespanDays */
    override var oldGuidesLifespanDays: Int by settings.int("oldGuideLifespan", 3 )
}