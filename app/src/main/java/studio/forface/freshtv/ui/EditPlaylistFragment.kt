package studio.forface.freshtv.ui

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.viewModel
import org.koin.core.parameter.parametersOf
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.frameworkcomponents.RootFragment
import studio.forface.freshtv.commonandroid.utils.applyWithTransition
import studio.forface.freshtv.uimodels.SourceFileUiModel
import studio.forface.freshtv.ui.EditPlaylistFragment.Mode.*
import studio.forface.freshtv.ui.EditPlaylistFragment.State.*
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
            R.drawable.ic_save_black,
            R.string.action_save,
            showOnStart = false
    ) {
        when ( mode ) {
            CREATE -> editPlaylistViewModel.create()
            EDIT -> editPlaylistViewModel.save()
        }
    }

    /** @return a NULLABLE [getView] casted as [ConstraintLayout] */
    private val layout get() = view as? ConstraintLayout

    /** The [EditPlaylistFragment.Mode] */
    private val mode by lazy { if ( args.playlistPath == null ) CREATE else EDIT }

    /** @see RootFragment.titleRes */
    override val titleRes: Int? get() = when ( mode ) {
        CREATE -> R.string.title_add_playlist
        EDIT ->   R.string.title_edit_playlist
    }

    /** When the `Activity` is created */
    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )

        editPlaylistViewModel.state.observeData {
            when ( it ) {

                CHOOSE_TYPE -> {
                    fab?.hide()
                    layout?.applyWithTransition( R.layout.fragment_source_file_edit_state_choose_type )
                }
                EDITING -> {
                    fab?.hide()
                    layout?.applyWithTransition( R.layout.fragment_source_file_edit_state_editing )
                }
                READY_TO_SAVE -> fab?.show()
            }
        }

        editPlaylistViewModel.playlist.observe {
            doOnData( ::onPlaylist )
            doOnError { notifier.error( it ) }
        }
    }

    /** When the [SourceFileUiModel] Playlist is received from [EditPlaylistViewModel] */
    private fun onPlaylist( playlist: SourceFileUiModel ) {
        // TODO
    }

    /** An enum for the mode of the [EditPlaylistFragment] */
    enum class Mode { CREATE, EDIT }

    /** An enum for the current state of the [EditPlaylistFragment] */
    enum class State { CHOOSE_TYPE, EDITING, READY_TO_SAVE }
}