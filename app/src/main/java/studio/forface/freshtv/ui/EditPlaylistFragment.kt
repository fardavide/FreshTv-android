package studio.forface.freshtv.ui

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.viewModel
import org.koin.core.parameter.parametersOf
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.frameworkcomponents.RootFragment
import studio.forface.freshtv.uimodels.SourceFileUiModel
import studio.forface.freshtv.viewmodels.EditPlaylistViewModel

/**
 * @author Davide Giuseppe Farella
 * A `Fragment` for Edit or Create a `Playlist`
 */
internal class EditPlaylistFragment: RootFragment( R.layout.fragment_source_file_edit ) {

    /** A reference to [EditPlaylistFragmentArgs] for get [EditPlaylistFragmentArgs.playlistPath] */
    private val args by navArgs<EditPlaylistFragmentArgs>()

    /** A reference to [EditPlaylistViewModel] */
    private val editPlaylistViewModel
            by viewModel<EditPlaylistViewModel> { parametersOf( args.playlistPath ) }

    /** @see RootFragment.fabParams */
    override val fabParams: FabParams get() = FabParams(

    )

    /** When the `Activity` is created */
    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )

        editPlaylistViewModel.playlist.observe {
            doOnData( ::onPlaylist )
            doOnError { notifier.error( it ) }
        }
    }

    /** When the [SourceFileUiModel] Playlist is received from [EditPlaylistViewModel] */
    private fun onPlaylist( playlist: SourceFileUiModel ) {
        // TODO
    }
}