package studio.forface.freshtv.viewmodels

import androidx.lifecycle.ViewModel
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.domain.usecases.FavoritedChannel
import studio.forface.freshtv.commonandroid.interactors.ChannelChangeFavoriteInteractor
import studio.forface.freshtv.presenters.TvChannelsPresenter
import studio.forface.freshtv.uimodels.TvChannelUiModel
import studio.forface.viewstatestore.paging.LockedPagedViewStateStore
import studio.forface.viewstatestore.paging.PagedViewStateStore

/**
 * A [ViewModel] that get `TvChannel`s
 * Inherit from [ScopedViewModel]
 *
 * @author Davide Giuseppe Farella
 */
internal class TvChannelsViewModel(
        private val groupName: String,
        private val presenter: TvChannelsPresenter,
        private val interactor: ChannelChangeFavoriteInteractor
): ScopedViewModel() {

    /** A [LockedPagedViewStateStore] of [TvChannelsViewModel] */
    val channels = PagedViewStateStore<TvChannelUiModel>(20 ).lock

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