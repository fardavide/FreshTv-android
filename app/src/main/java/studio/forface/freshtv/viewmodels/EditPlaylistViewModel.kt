package studio.forface.freshtv.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.commonandroid.viewstate.*
import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.domain.entities.Url
import studio.forface.freshtv.domain.entities.Validable
import studio.forface.freshtv.domain.usecases.RefreshChannels
import studio.forface.freshtv.domain.utils.EMPTY_STRING
import studio.forface.freshtv.entities.SourceFilePath
import studio.forface.freshtv.interactors.EditPlaylistInteractor
import studio.forface.freshtv.presenters.PlaylistPresenter
import studio.forface.freshtv.services.RefreshChannelsWorker
import studio.forface.freshtv.ui.EditPlaylistFragment
import studio.forface.freshtv.ui.EditPlaylistFragment.State.*
import studio.forface.freshtv.uimodels.SourceFileEditFormUiModel
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

    /** A [ViewStateStore] of [SourceFileEditFormUiModel] */
    val form = ViewStateStore<SourceFileEditFormUiModel>()

    /** A [ViewStateStore] of [SourceFileUiModel] */
    val playlist = ViewStateStore<SourceFileUiModel>()

    /** A [ViewStateStore] of [EditPlaylistFragment.State] */
    val state = ViewStateStore<EditPlaylistFragment.State>( ChooseType )

    /** The [SourceFileUiModel.shownName] */
    var name: CharSequence = EMPTY_STRING

    /**
     * The [SourceFileUiModel.fullPath]
     * @see onPathChange
     */
    var path: CharSequence = EMPTY_STRING
        set( value ) {
            field = value
            onPathChange()
        }

    /**
     * The [SourceFileUiModel.sourceType]
     * @see onTypeSet
     */
    var type: SourceFile.Type? = null
        set( value ) {
            field = value
            onTypeSet()
        }

    init {
        if ( playlistPath != null ) {
            playlist.setLoading()
            launch {
                withContext( IO ) { runCatching { presenter( playlistPath ) } }
                        .onSuccess( ::onPlaylistReceived )
                        .onFailure { playlist.setError( it ) }
            }
        }
    }

    /** Create a `Playlist` */
    fun create() {
        interactor.add( path.toString(), type!!, name.toString() )
        onSuccess()
    }

    /** Save the edited `Playlist` and [RefreshChannelsWorker.enqueue] */
    fun save() {
        interactor.update( path.toString(), name.toString() )
        onSuccess()
    }

    /** Called when [create] or [save] has succeed */
    private fun onSuccess() {
        RefreshChannelsWorker.enqueue( path.toString() )
        state.postData( SaveCompleted )
    }

    /** When [SourceFileUiModel] is received from [PlaylistPresenter] */
    private fun onPlaylistReceived( uiModel: SourceFileUiModel ) {
        with( uiModel ) {
            playlist.setData( this )
            path = fullPath
            name = shownName
            type = sourceType
        }
    }

    /** When [path] has changed */
    private fun onPathChange() {
        val isValid = when ( type!! ) {
            SourceFile.Type.LOCAL -> SourceFilePath( path ).valid
            SourceFile.Type.REMOTE -> {
                val result = Url( path ).validate()
                onPathValidation( result )
                result is Validable.Result.Success
            }
        }
        state.setData( if ( isValid ) ReadyToSave else Editing( type!! ) )
        if ( isValid ) {
            if ( type == SourceFile.Type.LOCAL ) {
                val oldData = form.state().data ?: SourceFileEditFormUiModel()
                val newData = oldData.copy( path = path )
                if ( oldData != newData ) form.setData( newData )
            }
        }
    }

    /** When [path] is validated */
    private fun onPathValidation( result: Validable.Result ) {
        val errorRes = when ( result ) {
            is Validable.Result.Failure -> when ( result as Url.Failure ) {
                is Url.Failure.BadFormat -> R.string.url_bad_format
                is Url.Failure.Empty -> R.string.url_empty
                is Url.Failure.NoSchema -> R.string.url_no_schema
            }
            is Validable.Result.Success -> null
        }
        val oldData = form.state().data ?: SourceFileEditFormUiModel()
        val newData = oldData.copy( urlError = errorRes )
        if ( oldData != newData ) form.setData( newData )
    }

    /** When [type] has been set */
    private fun onTypeSet() {
        state.setData( Editing( type!! ) )
    }
}