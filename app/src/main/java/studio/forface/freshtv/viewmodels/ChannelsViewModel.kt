package studio.forface.freshtv.viewmodels

import androidx.lifecycle.ViewModel
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.commonandroid.interactors.ChannelChangeFavoriteInteractor
import studio.forface.freshtv.domain.usecases.FavoritedChannel
import studio.forface.freshtv.presenters.MovieChannelsPresenter
import studio.forface.freshtv.presenters.TvChannelsPresenter
import studio.forface.freshtv.uimodels.MovieChannelUiModel
import studio.forface.freshtv.uimodels.TvChannelUiModel
import studio.forface.viewstatestore.paging.LockedPagedViewStateStore
import studio.forface.viewstatestore.paging.PagedViewStateStore

/**
 * An abstract [ViewModel] that get `Channel`s
 * Inherit from [ScopedViewModel]
 *
 * @author Davide Giuseppe Farella
 */
internal abstract class ChannelsViewModel(
    private val favoriteInteractor: ChannelChangeFavoriteInteractor
): ScopedViewModel() {

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
    groupName: String,
    private val presenter: MovieChannelsPresenter,
    favoriteInteractor: ChannelChangeFavoriteInteractor
): ChannelsViewModel( favoriteInteractor ) {

    /** A [LockedPagedViewStateStore] of [MovieChannelsViewModel] */
    val channels = PagedViewStateStore<MovieChannelUiModel>(20 ).lock

    init {
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
    groupName: String,
    private val presenter: TvChannelsPresenter,
    favoriteInteractor: ChannelChangeFavoriteInteractor
): ChannelsViewModel( favoriteInteractor ) {

    /** A [LockedPagedViewStateStore] of [TvChannelsViewModel] */
    val channels = PagedViewStateStore<TvChannelUiModel>(20 ).lock

    init {
        channels.setLoading()
        runCatching { presenter( groupName ) }
            .onSuccess { channels.setDataSource( it ) }
            .onFailure { channels.setError( it ) }
    }
}