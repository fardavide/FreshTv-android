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
import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.domain.utils.EMPTY_STRING
import studio.forface.freshtv.entities.SourceFilePath
import studio.forface.freshtv.interactors.EditPlaylistInteractor
import studio.forface.freshtv.presenters.PlaylistPresenter
import studio.forface.freshtv.ui.EditPlaylistFragment
import studio.forface.freshtv.ui.EditPlaylistFragment.State.*
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

    /** A [ViewStateStore] of [EditPlaylistFragment.State] */
    val state = ViewStateStore( CHOOSE_TYPE )

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
    }

    /** Save the edited `Playlist` */
    fun save() {
        interactor.update( path.toString(), name.toString() )
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
        if ( SourceFilePath( path ).valid ) state.setData( READY_TO_SAVE )
    }

    /** When [type] has been set */
    private fun onTypeSet() {
        state.setData( EDITING )
    }
}