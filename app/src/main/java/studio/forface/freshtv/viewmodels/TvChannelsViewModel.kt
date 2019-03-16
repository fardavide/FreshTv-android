package studio.forface.freshtv.viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.domain.usecases.FavoritedChannel
import studio.forface.freshtv.interactors.ChannelChangeFavoriteInteractor
import studio.forface.freshtv.presenters.TvChannelsPresenter
import studio.forface.freshtv.uimodels.TvChannelUiModel
import studio.forface.viewstatestore.ViewStateStore
import studio.forface.viewstatestore.paging.PagedViewStateStore
import studio.forface.viewstatestore.setError
import studio.forface.viewstatestore.setLoading

/**
 * @author Davide Giuseppe Farella
 * A [ViewModel] that get `TvChannel`s
 *
 * Inherit from [ScopedViewModel]
 */
internal class TvChannelsViewModel(
        private val presenter: TvChannelsPresenter,
        private val interactor: ChannelChangeFavoriteInteractor,
        private val groupName: String?
): ScopedViewModel() {

    /** A [ViewStateStore] of [PagedList] of [TvChannelsViewModel] */
    val channels = PagedViewStateStore<TvChannelUiModel>(20 )

    init {
        channels.setLoading()
        runCatching { presenter( groupName ) }
                .onSuccess { channels.setDataSource( it ) }
                .onFailure { channels.setError( it ) }
    }

    /** Change the Favorite state of a `Channel` */
    fun setFavoriteChannel( favoritedChannel: FavoritedChannel ) {
        interactor( favoritedChannel )
    }
}