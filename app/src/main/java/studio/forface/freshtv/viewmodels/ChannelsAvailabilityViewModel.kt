package studio.forface.freshtv.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.domain.utils.delay
import studio.forface.freshtv.presenters.ChannelsAvailabilityPresenter
import studio.forface.freshtv.presenters.invoke
import studio.forface.freshtv.uimodels.ChannelsAvailabilityUiModel
import studio.forface.viewstatestore.LockedViewStateStore
import studio.forface.viewstatestore.ViewStateStore

/**
 * @author Davide Giuseppe Farella
 * A [ViewModel] that checks the availability of Channels
 *
 * Inherit from [ScopedViewModel]
 */
internal class ChannelsAvailabilityViewModel(
    private val presenter: ChannelsAvailabilityPresenter
): ScopedViewModel() {

    /** A [LockedViewStateStore] of [ChannelsAvailabilityUiModel] */
    val channelsAvailability = ViewStateStore<ChannelsAvailabilityUiModel>().lock

    init {
        // When ViewModel is instantiated, start observing for UiModels
        launch( IO ) { startObserving() }
    }

    private suspend fun startObserving() {
        channelsAvailability.postLoading()

        runCatching {

            // Receive Models
            for ( uiModel in presenter { observe() } )
                channelsAvailability.postData( uiModel )

        // If some error occurs, notify it, wait and then try again
        }.onFailure {
            channelsAvailability.postError( it )
            delay( DEFAULT_ERROR_DELAY )
            startObserving()
        }
    }
}