package studio.forface.freshtv.player.viewmodels

import kotlinx.coroutines.launch
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.player.interactors.UpdateChannelSourceFailureInteractor
import studio.forface.freshtv.player.presenters.ChannelSourcePresenter
import studio.forface.freshtv.player.uiModels.ChannelSourceUiModel
import studio.forface.viewstatestore.ViewStateStore
import studio.forface.viewstatestore.postData
import studio.forface.viewstatestore.postError
import studio.forface.viewstatestore.setLoading

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

    /** A [ViewStateStore] of [ChannelSourceUiModel] */
    val source = ViewStateStore<ChannelSourceUiModel>( dropOnSame = false )

    init {
        source.setLoading()
        launch {
            runCatching {
                for ( uiModel in presenter( channelId ) )
                    source.postData( uiModel )
            }.onFailure { source.postError( it ) }
        }
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