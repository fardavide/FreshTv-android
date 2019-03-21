package studio.forface.freshtv.viewmodels

import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.database.getStringOrNull
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.domain.entities.Url
import studio.forface.freshtv.domain.entities.Validable
import studio.forface.freshtv.domain.utils.EMPTY_STRING
import studio.forface.freshtv.domain.utils.handle
import studio.forface.freshtv.entities.SourceFilePath
import studio.forface.freshtv.interactors.AbsEditSourceFileInteractor
import studio.forface.freshtv.presenters.AbsSourceFilePresenter
import studio.forface.freshtv.ui.AbsEditSourceFileFragment
import studio.forface.freshtv.ui.AbsEditSourceFileFragment.State.*
import studio.forface.freshtv.uimodels.SourceFileEditFormUiModel
import studio.forface.freshtv.uimodels.SourceFileUiModel
import studio.forface.viewstatestore.*

/**
 * @author Davide Giuseppe Farella
 * A [ViewModel] get and edit as Source File
 *
 * Inherit from [ScopedViewModel]
 */
internal abstract class AbsEditSourceFileViewModel(
        private val interactor: AbsEditSourceFileInteractor,
        private val presenter: AbsSourceFilePresenter,
        private val filePath: String?
): ScopedViewModel() {

    /** A [ViewStateStore] of [SourceFileEditFormUiModel] */
    val form = ViewStateStore<SourceFileEditFormUiModel>()

    /** A [ViewStateStore] of [SourceFileUiModel] */
    val sourceFile = ViewStateStore<SourceFileUiModel>()

    /** A [ViewStateStore] of [AbsEditSourceFileFragment.State] */
    val state = ViewStateStore<AbsEditSourceFileFragment.State>()

    /** The [SourceFileUiModel.shownName] */
    var name: CharSequence = EMPTY_STRING

    /** @return [ViewState.data] of [form] if not null, else a new [SourceFileEditFormUiModel] */
    private val lastForm get() = form.state()?.data ?: SourceFileEditFormUiModel()

    /**
     * The [SourceFileUiModel.fullPath]
     * @see onPathChange
     */
    private var path: CharSequence = EMPTY_STRING
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
        if ( filePath != null ) {
            sourceFile.setLoading()
            launch {
                withContext( IO ) { runCatching { presenter( filePath ) } }
                        .onSuccess( ::onSourceFileReceived )
                        .onFailure { sourceFile.setError( it ) }
            }
        } else state.setData( ChooseType )
    }

    /** Enqueue the relative `Worker` */
    protected abstract fun enqueueWorker( filePath: String )

    /** Cancel the relative `Worker` */
    protected abstract fun cancelWorker( filePath: String )

    /** Create and set a Source File's path from [Uri] */
    fun AbsEditSourceFileFragment<*>.setFileUri( uri: Uri ) {
        val newPath = uri.toString()
        if ( newPath == path ) return

        path = newPath
        // Get file name from ContentResolver
        requireContext().contentResolver
            .query( uri,null,null,null,null )?.use { cursor ->
                cursor.moveToFirst()
                val name = cursor.getStringOrNull( cursor.getColumnIndex( OpenableColumns.DISPLAY_NAME ) )
                form.setData( lastForm.copy( name = name ) )
            }
    }

    /** Set a Source File's path from [CharSequence] */
    fun setFilePath( path: CharSequence ) {
        if ( path.toString() == this.path ) return

        this.path = path
        // Get file name from path
        val startIndex = with( path.lastIndexOf('/' ) ) {
            if ( this >= 0 ) this +1 else return
        }
        val endIndex = with( path.lastIndexOf('.' ) ) {
            if ( this > startIndex ) this else path.length
        }
        val name = handle { path.substring( startIndex, endIndex ) }
        form.setData( lastForm.copy( name = name ) )
    }

    /** Create an `EPG` */
    fun create() {
        interactor.add( path.toString(), type!!, name.toString() )
        onSuccess()
    }

    /** Save the edited `EPG` */
    fun save() {
        interactor.update( path.toString(), name.toString() )
        onSuccess()
    }

    /** Delete the current `EPG` */
    fun delete() {
        interactor.remove( path.toString() )
        cancelWorker( path.toString() )
    }

    /** Called when [create] or [save] has succeed */
    private fun onSuccess() {
        enqueueWorker( path.toString() )
        state.postData( SaveCompleted )
    }

    /** When [SourceFileUiModel] is received from [AbsSourceFilePresenter] */
    private fun onSourceFileReceived( uiModel: SourceFileUiModel ) {
        with( uiModel ) {
            type = sourceType
            path = fullPath
            name = shownName
            sourceFile.setData(this )
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
            if ( type == SourceFile.Type.LOCAL )
                form.setData( lastForm.copy( path = path ) )
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
        val oldData = form.state()?.data ?: SourceFileEditFormUiModel()
        val newData = oldData.copy( urlError = errorRes )
        if ( oldData != newData ) form.setData( newData )
    }

    /** When [type] has been set */
    private fun onTypeSet() {
        state.setData( Editing( type!! ) )
    }
}