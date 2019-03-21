package studio.forface.freshtv.viewmodels

import androidx.lifecycle.ViewModel
import studio.forface.freshtv.interactors.EditPlaylistInteractor
import studio.forface.freshtv.presenters.PlaylistPresenter
import studio.forface.freshtv.services.RefreshChannelsWorker

/**
 * @author Davide Giuseppe Farella
 * A [ViewModel] get and edit a `Playlist`
 *
 * Inherit from [AbsEditSourceFileViewModel]
 */
internal class EditPlaylistViewModel(
        interactor: EditPlaylistInteractor,
        presenter: PlaylistPresenter,
        playlistPath: String?
): AbsEditSourceFileViewModel( interactor, presenter, playlistPath ) {

    /** Enqueue [RefreshChannelsWorker] for the given [filePath] */
    override fun enqueueWorker( filePath: String ) {
        RefreshChannelsWorker.enqueue( filePath )
    }

    /** Cancel [RefreshChannelsWorker] for the given [filePath] */
    override fun cancelWorker( filePath: String ) {
        RefreshChannelsWorker.cancel( filePath )
    }
}