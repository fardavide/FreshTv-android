package studio.forface.freshtv.viewmodels

import androidx.lifecycle.ViewModel
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.commonandroid.interactors.ChannelChangeFavoriteInteractor
import studio.forface.freshtv.domain.entities.ChannelIdAndPosition
import studio.forface.freshtv.domain.usecases.FavoritedChannel
import studio.forface.freshtv.interactors.SaveLastChannelInteractor
import studio.forface.freshtv.interactors.SaveLastMovieChannelInteractor
import studio.forface.freshtv.interactors.SaveLastTvChannelInteractor
import studio.forface.freshtv.presenters.MovieChannelsPresenter
import studio.forface.freshtv.presenters.TvChannelsPresenter
import studio.forface.freshtv.uimodels.MovieChannelUiModel
import studio.forface.freshtv.uimodels.TvChannelUiModel
import studio.forface.viewstatestore.LockedViewStateStore
import studio.forface.viewstatestore.ViewStateStore
import studio.forface.viewstatestore.paging.LockedPagedViewStateStore
import studio.forface.viewstatestore.paging.PagedViewStateStore

/**
 * An abstract [ViewModel] that get `Channel`s
 * Inherit from [ScopedViewModel]
 *
 * @author Davide Giuseppe Farella
 */
internal abstract class ChannelsViewModel(
    private val favoriteInteractor: ChannelChangeFavoriteInteractor,
    private val lastChannelInteractor: SaveLastChannelInteractor
): ScopedViewModel() {

    /** A [LockedViewStateStore] of [ChannelIdAndPosition] */
    val lastPosition = ViewStateStore<ChannelIdAndPosition>().lock

    /** Load the required `Channel`s */
    protected abstract fun loadChannels()

    /** @return OPTIONAL last [ChannelIdAndPosition] */
    protected abstract fun getLastPosition(): ChannelIdAndPosition?

    /** Called from UI when new Channels are delivered */
    fun requestLastPosition() {
        getLastPosition()?.let { lastPosition.setData( it ) }
    }

    /** Save the given [channelId] as last channel id */
    fun saveLastChannelIdAndPosition( channelId: String?, position: Int ) {
        lastChannelInteractor.saveLastChannelIdAndPosition( channelId, position )
    }

    /** Change the Favorite state of a `Channel` */
    fun setFavoriteChannel( favoritedChannel: FavoritedChannel ) {
        favoriteInteractor( favoritedChannel )
    }
}

/**
 * A [ViewModel] that get `MovieChannel`s
 * Inherit from [ChannelsViewModel]
 *
 * @author Davide Giuseppe Farella
 */
internal class MovieChannelsViewModel(
    private val groupName: String,
    private val presenter: MovieChannelsPresenter,
    favoriteInteractor: ChannelChangeFavoriteInteractor,
    lastChannelInteractor: SaveLastMovieChannelInteractor
): ChannelsViewModel( favoriteInteractor, lastChannelInteractor ) {

    /** A [LockedPagedViewStateStore] of [MovieChannelsViewModel] */
    val channels = PagedViewStateStore<MovieChannelUiModel>(20 ).lock

    init {
        loadChannels()
    }

    /** @return OPTIONAL last [ChannelIdAndPosition] */
    override fun getLastPosition() = presenter.getLastPosition()

    /** Load the required `MovieChannel`s */
    override fun loadChannels() {
        channels.setLoading()
        runCatching { presenter( groupName ) }
            .onSuccess { channels.setDataSource( it ) }
            .onFailure { channels.setError( it ) }
    }
}

/**
 * A [ViewModel] that get `TvChannel`s
 * Inherit from [ChannelsViewModel]
 *
 * @author Davide Giuseppe Farella
 */
internal class TvChannelsViewModel(
    private val groupName: String,
    private val presenter: TvChannelsPresenter,
    favoriteInteractor: ChannelChangeFavoriteInteractor,
    lastChannelInteractor: SaveLastTvChannelInteractor
): ChannelsViewModel( favoriteInteractor, lastChannelInteractor ) {

    /** A [LockedPagedViewStateStore] of [TvChannelsViewModel] */
    val channels = PagedViewStateStore<TvChannelUiModel>(20 ).lock

    init {
        loadChannels()
    }

    /** @return OPTIONAL last [ChannelIdAndPosition] */
    override fun getLastPosition() = presenter.getLastPosition()

    /** Load the required `TvChannel`s */
    override fun loadChannels() {
        channels.setLoading()
        runCatching { presenter( groupName ) }
            .onSuccess { channels.setDataSource( it ) }
            .onFailure { channels.setError( it ) }
    }
}