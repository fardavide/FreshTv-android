package studio.forface.freshtv.preferences.presenters

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import studio.forface.freshtv.commonandroid.mappers.invoke
import studio.forface.freshtv.commonandroid.mappers.map
import studio.forface.freshtv.domain.gateways.AppSettings
import studio.forface.freshtv.domain.gateways.SettingsListener
import studio.forface.freshtv.domain.usecases.HasTvGuides
import studio.forface.freshtv.preferences.mappers.GuidesDatabaseStateUiModelMapper
import studio.forface.freshtv.preferences.uimodels.GuidesDatabaseStateUiModel

/**
 * A Presenter for get Preferences for toggle Night Mode
 *
 * @author Davide Giuseppe Farella
 */
internal interface ToggleNightModePresenter {

    /** @return [Boolean] */
    fun nightModeEnabled(): Boolean

    /** @return [ReceiveChannel] of [Boolean] */
    fun observeNightModeEnabled() : ReceiveChannel<Boolean>

    /** Remove the listener from [AppSettings] */
    fun removeNightModeListener()
}

/** Implementation of [ToggleNightModePresenter] */
internal class ToggleNightModePresenterImpl(
    private val appSettings: AppSettings
) : ToggleNightModePresenter {

    /** A reference to [SettingsListener] */
    private var listener: SettingsListener? = null

    /** @return [Boolean] */
    override fun nightModeEnabled(): Boolean = appSettings.nightMode

    /** @return [ReceiveChannel] of [Boolean] */
    override fun observeNightModeEnabled() : ReceiveChannel<Boolean> {
        val channel = Channel<Boolean>()
        listener = appSettings.addListener( AppSettings::nightMode ) { channel.offer( it ) }
        return channel
    }

    /** Remove the listener from [AppSettings] */
    override fun removeNightModeListener() {
        listener?.let{ appSettings.removeListener( it ) }
        listener = null
    }
}