package studio.forface.freshtv.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_source_file_edit.*
import org.koin.androidx.viewmodel.ext.viewModel
import org.koin.core.parameter.parametersOf
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.frameworkcomponents.RootFragment
import studio.forface.freshtv.commonandroid.utils.applyWithTransition
import studio.forface.freshtv.commonandroid.utils.errorRes
import studio.forface.freshtv.commonandroid.utils.onTextChange
import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.domain.entities.SourceFile.Type.LOCAL
import studio.forface.freshtv.domain.entities.SourceFile.Type.REMOTE
import studio.forface.freshtv.ui.EditPlaylistFragment.Mode.CREATE
import studio.forface.freshtv.ui.EditPlaylistFragment.Mode.EDIT
import studio.forface.freshtv.ui.EditPlaylistFragment.State.*
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
            R.drawable.ic_save_black,
            R.string.action_save,
            showOnStart = editPlaylistViewModel.state.state().data is ReadyToSave
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

        // Observe to a State for the Fragment
        editPlaylistViewModel.state.observeData { state ->
            if ( state is ReadyToSave ) fab?.show()
            else fab?.hide()

            val layoutRes = when ( state ) {
                is ChooseType -> R.layout.fragment_source_file_edit_state_choose_type
                is Editing -> when( state.type ) {
                    LOCAL -> R.layout.fragment_source_file_edit_state_editing_file
                    REMOTE -> R.layout.fragment_source_file_edit_state_editing_web
                }
                else -> null
            }
            layoutRes?.let { layout?.applyWithTransition( it ) }
        }

        // Observe to Form for the Fragment
        editPlaylistViewModel.form.observeData {
            editSourceFileUrlLayout.errorRes = it.urlError
            it.path?.let { path -> editSourceFilePathEditText.setText( path ) }
        }

        // Observe to a previously store `Playlist`
        editPlaylistViewModel.playlist.observe {
            doOnData( ::onPlaylist )
            doOnError { notifier.error( it ) }
        }
    }

    /** When the [EditPlaylistFragment]s [View] is created */
    override fun onViewCreated( view: View, savedInstanceState: Bundle? ) {
        super.onViewCreated( view, savedInstanceState )
        editSourceFilePathLayout.isEnabled = false
        with( editPlaylistViewModel ) {
            editSourceFileNameEditText.onTextChange { name = it }
            editSourceFileUrlEditText.onTextChange { path = it }
            editSourceFileFromFileButton.setOnClickListener { type = LOCAL }
            editSourceFileFromWebButton.setOnClickListener { type = REMOTE }
            editSourceFilePickFileButton.setOnClickListener { pickFile() }
        }
    }

    /** When a File is selected as Source path */
    private fun onFileSelected( uri: Uri ) {
        editPlaylistViewModel.path = uri.encodedPath!!
    }

    /** When the [SourceFileUiModel] Playlist is received from [EditPlaylistViewModel] */
    private fun onPlaylist( playlist: SourceFileUiModel ) {
        editSourceFileNameEditText.setText( playlist.shownName )
        editSourceFilePathEditText.setText( playlist.fullPath )
        editSourceFileUrlEditText.setText( playlist.fullPath )
    }

    /** Pick a File to be used as `Playlist`s path */
    private fun pickFile() {
        // TODO
    }

    /** An enum for the mode of the [EditPlaylistFragment] */
    enum class Mode { CREATE, EDIT }

    /** An enum for the current state of the [EditPlaylistFragment] */
    sealed class State {
        object ChooseType : State()
        class Editing( val type: SourceFile.Type ) : State()
        object ReadyToSave : State()
    }
}