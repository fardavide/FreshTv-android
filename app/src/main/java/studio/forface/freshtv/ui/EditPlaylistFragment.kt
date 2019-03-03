package studio.forface.freshtv.ui

import android.Manifest.permission
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_source_file_edit.*
import org.koin.androidx.viewmodel.ext.viewModel
import org.koin.core.parameter.parametersOf
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.frameworkcomponents.RootFragment
import studio.forface.freshtv.commonandroid.utils.*
import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.domain.entities.SourceFile.Type.LOCAL
import studio.forface.freshtv.domain.entities.SourceFile.Type.REMOTE
import studio.forface.freshtv.ui.EditPlaylistFragment.Mode.CREATE
import studio.forface.freshtv.ui.EditPlaylistFragment.Mode.EDIT
import studio.forface.freshtv.ui.EditPlaylistFragment.State.*
import studio.forface.freshtv.uimodels.SourceFileUiModel
import studio.forface.freshtv.viewmodels.EditPlaylistViewModel
import studio.forface.materialbottombar.dsl.drawer
import studio.forface.materialbottombar.panels.params.*

/**
 * @author Davide Giuseppe Farella
 * A `Fragment` for Edit or Create a `Playlist`
 *
 * Inherit from [RootFragment]
 */
internal class EditPlaylistFragment: RootFragment( R.layout.fragment_source_file_edit ) {

    /**
     * A reference to [EditPlaylistFragmentArgs] for get the `playlistPath` of the current editing `Playlist`
     * from [navArgs]
     */
    private val args by navArgs<EditPlaylistFragmentArgs>()

    /** A reference to [EditPlaylistViewModel] for edit a `Playlist` element */
    private val editPlaylistViewModel
            by viewModel<EditPlaylistViewModel> {  parametersOf( args.playlistPath ) }

    /** @see RootFragment.fabParams */
    override val fabParams: FabParams get() = FabParams(
            R.drawable.ic_save_black,
            R.string.action_save,
            showOnStart = editPlaylistViewModel.state.state()?.data is ReadyToSave
    ) {
        when ( mode ) {
            CREATE -> editPlaylistViewModel.create()
            EDIT -> editPlaylistViewModel.save()
        }
    }

    /** @return a NULLABLE [getView] casted as [ConstraintLayout] */
    private val layout get() = view as? ConstraintLayout

    /** @see RootFragment.menuRes */
    override val menuRes: Int? get() = ( mode == EDIT ) { R.menu.menu_delete }

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
            when ( state ) {
                is SaveCompleted -> navController.popBackStack()
                is ReadyToSave -> fab?.show()
                else -> fab?.hide()
            }

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
            editSourceFilePickFileButton.setOnClickListener { pickFileWithPermissions() }
        }
    }

    /**
     * When a [MenuItem] is selected from Options Menu.
     * @see menuRes
     */
    override fun onOptionsItemSelected( item: MenuItem ): Boolean {
        when ( item.itemId ) {

            R.id.action_delete -> showPanel( item.itemId , drawer { // TODO use panel instead.
                header {
                    titleTextRes = R.string.prompt_delete_playlist
                    titleSpSize = 18f
                }
                body {
                    primaryItem( R.string.action_delete ) {
                        titleBold = true
                        titleColorRes = R.color.red_500
                        onClick {
                            dismissAndRemovePanel( item.itemId )
                            editPlaylistViewModel.delete()
                            navController.popBackStack()
                        }
                    }
                    primaryItem( R.string.action_cancel ) onClick { dismissAndRemovePanel( item.itemId ) }
                }
            } )
        }
        return super.onOptionsItemSelected( item )
    }

    /** When [startActivityForResult] return its result */
    override fun onActivityResult( requestCode: Int, resultCode: Int, resultData: Intent? ) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code PICK_FILE_REQUEST_CODE.
        // If the request code seen here doesn't match, it's the response to some other intent, and
        // the code below shouldn't run at all.

        if ( requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK ) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent provided to
            // this method as a parameter.
            // Pull that URI using resultData.getData().
            resultData?.data?.let { onFileSelected( it ) }
        }
    }

    /** When [requestPermissions] return its result */
    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        when ( requestCode ) {
            PICK_FILE_PERM_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ( grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED ) {
                    // permission was granted, yay!
                    pickFile()
                } else {
                    // permission denied, boo!
                    val ex = Exception( "Permissions denies: ${permissions.joinToString()}" )
                    notifier.error( ex, R.string.permission_denied ) {
                        actionNameRes = R.string.action_grant
                        actionBlock = { pickFileWithPermissions() }
                    }
                }
            }
        }
    }

    /** When a File is selected as Source path */
    private fun onFileSelected( uri: Uri ) {
        editPlaylistViewModel.path =
                Environment.getExternalStorageDirectory().absolutePath + uri.path!!
    }

    /** When the [SourceFileUiModel] Playlist is received from [EditPlaylistViewModel] */
    private fun onPlaylist( playlist: SourceFileUiModel ) {
        editSourceFileNameEditText.setText( playlist.shownName )
        editSourceFilePathEditText.setText( playlist.fullPath )
        editSourceFileUrlEditText.setText( playlist.fullPath )
    }

    /** Pick a File to be used as `Playlist`s path */
    private fun pickFile() {
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file browser.
        val intent = Intent( Intent.ACTION_OPEN_DOCUMENT ).apply {
            // Filter to only show results that can be "opened", such as a file ( as opposed to a
            // list of contacts or timezones )
            addCategory( Intent.CATEGORY_OPENABLE )

            // Filter to show all the file types.
            type = "*/*"
        }

        startActivityForResult( intent, PICK_FILE_REQUEST_CODE )
    }

    /** Request the needed permissions for [pickFile] */
    private fun pickFileWithPermissions() {
        val perm = permission.READ_EXTERNAL_STORAGE

        if ( checkSelfPermission( perm ) != PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.
            requestPermissions( arrayOf( perm ), PICK_FILE_PERM_REQUEST_CODE )

            // PICK_FILE_PERM_REQUEST_CODE is an app-defined int constant. The callback method
            // gets the result of the request.

        } else pickFile()
    }

    /** An enum for the mode of the [EditPlaylistFragment] */
    enum class Mode { CREATE, EDIT }

    /** An enum for the current state of the [EditPlaylistFragment] */
    sealed class State {
        object ChooseType : State()
        class Editing( val type: SourceFile.Type ) : State()
        object ReadyToSave : State()
        object SaveCompleted : State()
    }
}

/**
 * A request code for Permissions for pick file
 * @see EditPlaylistFragment.requestPermissions
 * @see EditPlaylistFragment.onRequestPermissionsResult
 */
private const val PICK_FILE_PERM_REQUEST_CODE = 7435

/**
 * A request code for pick file
 * @see EditPlaylistFragment.startActivityForResult
 * @see EditPlaylistFragment.onActivityResult
 */
private const val PICK_FILE_REQUEST_CODE = 7435