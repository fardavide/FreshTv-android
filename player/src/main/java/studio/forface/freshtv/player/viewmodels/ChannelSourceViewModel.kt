package studio.forface.freshtv.player.viewmodels

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.player.interactors.UpdateChannelSourceFailureInteractor
import studio.forface.freshtv.player.presenters.ChannelSourcePresenter
import studio.forface.freshtv.player.uiModels.ChannelSourceUiModel
import studio.forface.viewstatestore.LockedViewStateStore
import studio.forface.viewstatestore.ViewStateStore
import java.lang.System.currentTimeMillis

/**
 * A View Model for get a `Source` for a `Channel` and increment it in case of error.
 * Inherit from [ScopedViewModel]
 *
 * @author Davide Giuseppe Farella
 */
internal class ChannelSourceViewModel(
        private val channelId: String,
        private val presenter: ChannelSourcePresenter,
        private val interactor: UpdateChannelSourceFailureInteractor
) : ScopedViewModel() {

    private companion object {
        /** The minimum delay in milliseconds between [ChannelSourceUiModel] delivery via [source] */
        const val MIN_SOURCE_DELIVERY_DELAY = 5_000L
    }

    /** A [Long] for keep track of the last delivery of [ChannelSourceUiModel] */
    private var lastSourceDeliveryTime = 0L

    /** A [LockedViewStateStore] of [ChannelSourceUiModel] */
    val source = ViewStateStore<ChannelSourceUiModel>().lock

    init {
        source.setLoading()
        launch { runCatching {

            for ( uiModel in presenter( channelId ) )
                deliverSource( uiModel )

        }.onFailure { source.postError( it ) } }
    }

    /**
     * Check if [MIN_SOURCE_DELIVERY_DELAY] is passed since [lastSourceDeliveryTime], then send the given [uiModel] to
     * [source]
     */
    private suspend fun deliverSource( uiModel: ChannelSourceUiModel ) {
        val toWait = ( lastSourceDeliveryTime + MIN_SOURCE_DELIVERY_DELAY - currentTimeMillis() )
            .coerceAtLeast(0)

        delay( toWait )
        lastSourceDeliveryTime = currentTimeMillis()
        source.postData( uiModel )
    }

    /** Notify the failure of the given [url] via [UpdateChannelSourceFailureInteractor] */
    fun notifyFailure( url: String ) {
        interactor.increment( channelId, url )
    }

    /** Notify the success of the given [url] via [UpdateChannelSourceFailureInteractor] */
    fun notifySuccess( url: String ) {
        interactor.reset( channelId, url )
    }
}