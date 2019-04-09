package studio.forface.freshtv.ui

import androidx.annotation.StringRes
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_source_file_edit.*
import org.koin.androidx.viewmodel.ext.viewModel
import org.koin.core.parameter.parametersOf
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.ui.ParentFragment
import studio.forface.freshtv.ui.AbsEditSourceFileFragment.Mode.CREATE
import studio.forface.freshtv.ui.AbsEditSourceFileFragment.Mode.EDIT
import studio.forface.freshtv.viewmodels.AbsEditSourceFileViewModel
import studio.forface.freshtv.viewmodels.EditPlaylistViewModel

/**
 * @author Davide Giuseppe Farella
 * A `Fragment` for Edit or Create a `Playlist`
 *
 * Inherit from [AbsEditSourceFileFragment]
 */
internal class EditPlaylistFragment : AbsEditSourceFileFragment<EditPlaylistViewModel>() {

    companion object {

        /** @return `NavDirections` to this `Fragment` */
        fun directions( playlistPath: String? = null ) =
                HomeFragmentDirections.actionToEditPlaylistFragment( playlistPath )

        /** The name of [EditPlaylistFragmentArgs.playlistPath] */
        const val ARG_PLAYLIST_PATH = "playlistPath"
    }

    /**
     * A reference to [EditPlaylistFragmentArgs] for get the `playlistPath` of the current editing `Playlist`
     * from [navArgs]
     */
    override val args by navArgs<EditPlaylistFragmentArgs>()

    /**
     * A [StringRes] representing the message to show when asking for deletion confirmation
     * @see onOptionsItemSelected
     */
    override val confirmDeletionMessageRes get() = R.string.prompt_delete_playlist

    /** A reference to [EditPlaylistViewModel] for edit a `Playlist` element */
    override val editViewModel
            by viewModel<EditPlaylistViewModel> { parametersOf( filePath ) }

    /** @return an OPTIONAL [String] File Path from [EditPlaylistFragmentArgs.playlistPath] */
    override val filePath get() = args.playlistPath

    /** @see ParentFragment.titleRes */
    override val titleRes: Int? get() = when ( mode ) {
        CREATE -> R.string.title_add_playlist
        EDIT ->   R.string.title_edit_playlist
    }

    /** Setup the Texts of Views */
    override fun setupViewTexts() {
        editSourceFilePromptTextView.setText( R.string.load_playlist_from )
        editSourceFileNameLayout.hint = getText( R.string.prompt_playlist_name )
        editSourceFilePathLayout.hint = getText( R.string.prompt_playlist_path )
        editSourceFileUrlLayout.hint = getText( R.string.prompt_playlist_url )
    }

    /** A function called when [AbsEditSourceFileViewModel.state] is [AbsEditSourceFileFragment.State.SaveCompleted] */
    override fun onSaveComplete() {
        navController.popBackStack()
    }
}