package studio.forface.freshtv.viewmodels

import androidx.lifecycle.ViewModel
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.presenters.PlaylistsPresenter
import studio.forface.freshtv.uimodels.SourceFileUiModel
import studio.forface.viewstatestore.paging.LockedPagedViewStateStore
import studio.forface.viewstatestore.paging.PagedViewStateStore

/**
 * @author Davide Giuseppe Farella
 * A [ViewModel] that get `Playlist`s
 *
 * Inherit from [ScopedViewModel]
 */
internal class PlaylistsViewModel(
        private val presenter: PlaylistsPresenter
): ScopedViewModel() {

    /** A [LockedPagedViewStateStore] of [SourceFileUiModel] */
    val playlists = PagedViewStateStore<SourceFileUiModel>(50 ).lock

    init {
        playlists.setLoading()
        runCatching { presenter() }
                .onSuccess { playlists.setDataSource( it ) }
                .onFailure { playlists.setError( it ) }
    }
}