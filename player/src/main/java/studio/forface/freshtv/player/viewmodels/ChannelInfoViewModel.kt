package studio.forface.freshtv.player.viewmodels

import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.threeten.bp.Duration
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.entities.IChannel.Type.*
import studio.forface.freshtv.domain.utils.delay
import studio.forface.freshtv.domain.utils.mins
import studio.forface.freshtv.player.presenters.ChannelTypePresenter
import studio.forface.freshtv.player.presenters.TvGuidesPresenter
import studio.forface.freshtv.player.uiModels.ChannelInfoUiModel
import studio.forface.viewstatestore.LockedViewStateStore
import studio.forface.viewstatestore.ViewStateStore

/**
 * A `ViewModel` for get Channel's Info
 * Inherit from [ScopedViewModel]
 *
 * @author Davide Giuseppe Farella
 */
internal class ChannelInfoViewModel(
    private val channelId: String,
    typePresenter: ChannelTypePresenter,
    private val tvPresenter: TvGuidesPresenter
) : ScopedViewModel() {

    private companion object {
        /** A [Duration] representing the interval between refreshes */
        val REFRESH_INTERVAL = 2.mins
    }

    /** A [LockedViewStateStore] of [ChannelInfoUiModel] */
    val info = ViewStateStore<ChannelInfoUiModel>().lock

    init {
        launch {
            val type = typePresenter( channelId )
            while ( coroutineContext.isActive ) {
                refresh( type )
                delay( REFRESH_INTERVAL )
            }
        }
    }

    /** Refresh the [ChannelInfoUiModel] to publish to [info] */
    private suspend fun refresh( type: IChannel.Type ) {
        val getInfo = { when( type ) {
            MOVIE -> TODO("not implemented" )
            TV -> tvPresenter( channelId )
        } }
        runCatching( getInfo )
            .onSuccess { info.postData( it ) }
            .onFailure {
                info.postError( it )
                delay( DEFAULT_ERROR_DELAY )
                refresh( type )
            }
    }

}