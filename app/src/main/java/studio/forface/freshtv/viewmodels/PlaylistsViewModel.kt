package studio.forface.freshtv.viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.presenters.PlaylistsPresenter
import studio.forface.freshtv.presenters.TvChannelsPresenter
import studio.forface.freshtv.uimodels.SourceFileUiModel
import studio.forface.freshtv.uimodels.TvChannelUiModel
import studio.forface.viewstatestore.ViewStateStore
import studio.forface.viewstatestore.paging.PagedViewStateStore
import studio.forface.viewstatestore.setError
import studio.forface.viewstatestore.setLoading

/**
 * @author Davide Giuseppe Farella
 * A [ViewModel] that get `Playlist`s
 *
 * Inherit from [ScopedViewModel]
 */
internal class PlaylistsViewModel(
        private val presenter: PlaylistsPresenter
): ScopedViewModel() {

    /** A [ViewStateStore] of [PagedList] of [SourceFileUiModel] */
    val playlists = PagedViewStateStore<SourceFileUiModel>(50 )

    init {
        playlists.setLoading()
        runCatching { presenter() }
                .onSuccess { playlists.setDataSource( it ) }
                .onFailure { playlists.setError( it ) }
    }
}