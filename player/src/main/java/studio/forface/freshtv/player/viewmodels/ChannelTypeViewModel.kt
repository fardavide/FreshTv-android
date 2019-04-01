package studio.forface.freshtv.player.viewmodels

import kotlinx.coroutines.launch
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.player.presenters.ChannelTypePresenter
import studio.forface.viewstatestore.LockedViewStateStore
import studio.forface.viewstatestore.ViewStateStore

/**
 * A `ViewModel` for get Channel's Type
 *
 * Inherit from [ScopedViewModel]
 *
 * @author Davide Giuseppe Farella
 */
internal class ChannelTypeViewModel(
    private val channelId: String,
    presenter: ChannelTypePresenter
) : ScopedViewModel() {

    /** A [LockedViewStateStore] of [IChannel.Type] */
    val type = ViewStateStore<IChannel.Type>().lock

    init {
        launch {
            runCatching { presenter( channelId ) }
                .onSuccess { type.postData( it ) }
                .onFailure { type.postError( it ) }
        }
    }
}