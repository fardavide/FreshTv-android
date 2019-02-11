package studio.forface.freshtv.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.commonandroid.viewstate.ViewStateStore
import studio.forface.freshtv.commonandroid.viewstate.setData
import studio.forface.freshtv.commonandroid.viewstate.setError
import studio.forface.freshtv.commonandroid.viewstate.setLoading
import studio.forface.freshtv.presenters.ChannelsAvailabilityPresenter
import studio.forface.freshtv.uimodels.ChannelsAvailabilityUiModel

/**
 * @author Davide Giuseppe Farella
 * A [ViewModel] that checks the availability of Channels
 *
 * Inherit from [ScopedViewModel]
 */
internal class ChannelsAvailabilityViewModel(
    private val presenter: ChannelsAvailabilityPresenter
): ScopedViewModel() {

    /** A [ViewStateStore] of [ChannelsAvailabilityUiModel] */
    val channelsAvailability = ViewStateStore<ChannelsAvailabilityUiModel>()

    init {
        channelsAvailability.setLoading()
        launch {
            withContext( IO ) { runCatching { presenter() } }
                .onSuccess { channelsAvailability.setData( it ) }
                .onFailure { channelsAvailability.setError( it ) }
        }
    }
}