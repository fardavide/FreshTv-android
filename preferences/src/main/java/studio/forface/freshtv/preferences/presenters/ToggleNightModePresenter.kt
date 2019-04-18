package studio.forface.freshtv.preferences.presenters

import kotlinx.coroutines.channels.ReceiveChannel
import studio.forface.freshtv.domain.gateways.AppSettings
import studio.forface.freshtv.domain.gateways.observe

/**
 * A Presenter for get Preferences for toggle Night Mode
 *
 * @author Davide Giuseppe Farella
 */
internal interface ToggleNightModePresenter { // TODO use case

    /** @return [Boolean] */
    fun nightModeEnabled(): Boolean

    /** @return [ReceiveChannel] of [Boolean] */
    fun observeNightModeEnabled() : ReceiveChannel<Boolean>
}

/** Implementation of [ToggleNightModePresenter] */
internal class ToggleNightModePresenterImpl(
    private val appSettings: AppSettings
) : ToggleNightModePresenter {

    /** @return [Boolean] */
    override fun nightModeEnabled(): Boolean = appSettings.nightMode

    /** @return [ReceiveChannel] of [Boolean] */
    override fun observeNightModeEnabled() = appSettings.observe( AppSettings::nightMode )
}