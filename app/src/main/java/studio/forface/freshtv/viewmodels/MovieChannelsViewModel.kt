package studio.forface.freshtv.viewmodels

import androidx.lifecycle.ViewModel
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.domain.usecases.FavoritedChannel
import studio.forface.freshtv.interactors.ChannelChangeFavoriteInteractor
import studio.forface.freshtv.presenters.MovieChannelsPresenter
import studio.forface.freshtv.uimodels.MovieChannelUiModel
import studio.forface.viewstatestore.paging.LockedPagedViewStateStore
import studio.forface.viewstatestore.paging.PagedViewStateStore

/**
 * @author Davide Giuseppe Farella
 * A [ViewModel] that get `MovieChannel`s
 *
 * Inherit from [ScopedViewModel]
 */
internal class MovieChannelsViewModel(
        private val groupName: String,
        private val presenter: MovieChannelsPresenter,
        private val interactor: ChannelChangeFavoriteInteractor
): ScopedViewModel() {

    /** A [LockedPagedViewStateStore] of [MovieChannelsViewModel] */
    val channels = PagedViewStateStore<MovieChannelUiModel>(20 ).lock

    init {
        channels.setLoading()
        runCatching { presenter( groupName ) }
                .onSuccess { channels.setDataSource( it ) }
                .onFailure { channels.setError( it ) }
    }

    /** Change the Favorite state of a `Channel` */
    fun setFavoriteChannel( favoritedChannel: FavoritedChannel ) {
        interactor( favoritedChannel )
        channels.setLoading()
    }
}