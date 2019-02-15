package studio.forface.freshtv.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.commonandroid.viewstate.ViewStateStore
import studio.forface.freshtv.commonandroid.viewstate.setData
import studio.forface.freshtv.commonandroid.viewstate.setError
import studio.forface.freshtv.commonandroid.viewstate.setLoading
import studio.forface.freshtv.interactors.EditPlaylistInteractor
import studio.forface.freshtv.presenters.ChannelsAvailabilityPresenter
import studio.forface.freshtv.presenters.PlaylistPresenter
import studio.forface.freshtv.uimodels.ChannelsAvailabilityUiModel
import studio.forface.freshtv.uimodels.SourceFileUiModel

/**
 * @author Davide Giuseppe Farella
 * A [ViewModel] get and edit a `Playlist`
 *
 * Inherit from [ScopedViewModel]
 */
internal class EditPlaylistViewModel(
        private val interactor: EditPlaylistInteractor,
        private val presenter: PlaylistPresenter,
        private val playlistPath: String?
): ScopedViewModel() {

    /** A [ViewStateStore] of [SourceFileUiModel] */
    val playlist = ViewStateStore<SourceFileUiModel>()

    init {
        if ( playlistPath != null ) {
            playlist.setLoading()
            launch {
                withContext( IO ) { runCatching { presenter( playlistPath ) } }
                        .onSuccess { playlist.setData( it ) }
                        .onFailure { playlist.setError( it ) }
            }
        }
    }
}