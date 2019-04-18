package studio.forface.freshtv.interactors

import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.domain.usecases.*
import studio.forface.freshtv.services.RemovePlaylistWorker

/**
 * An Interactor for Add or Update a Source File
 *
 * @author Davide Giuseppe Farella
 */
internal interface EditSourceFileInteractor {

    /** Add a Source File with the given [path], [type] and [name] */
    fun add( path: String, type: SourceFile.Type, name: String? )

    /** Remove the Source File with the given [path] */
    fun remove( path: String )

    /** Update a Source File with the given [path] and [name] */
    fun update( path: String, name: String? )
}

/**
 * An Interactor for Add or Update an `EPG`
 * Inherit from [EditSourceFileInteractor]
 *
 * @author Davide Giuseppe Farella
 */
internal class EditEpgInteractor(
    private val addEpg: AddEpg,
    private val removeEpg: RemoveEpg,
    private val updateEpg: UpdateEpg
) : EditSourceFileInteractor {

    /** Add an `EPG` with the given [path], [type] and [name] */
    override fun add( path: String, type: SourceFile.Type, name: String? ) {
        addEpg( path, type, name ?: path )
    }

    /** Remove the `EPG` with the given [path] */
    override fun remove( path: String ) {
        removeEpg( path )
    }

    /** Update an `EPG` with the given [path] and [name] */
    override fun update( path: String, name: String? ) {
        updateEpg( path, name )
    }
}

/**
 * An Interactor for Add or Update a `Playlist`
 * Inherit from [EditSourceFileInteractor]
 *
 * @author Davide Giuseppe Farella
 */
internal class EditPlaylistInteractor(
    private val addPlaylist: AddPlaylist,
    private val updatePlaylist: UpdatePlaylist
) : EditSourceFileInteractor {

    /** Add a `Playlist` with the given [path], [type] and [name] */
    override fun add( path: String, type: SourceFile.Type, name: String? ) {
        addPlaylist( path, type,name ?: path )
    }

    /** Remove the `Playlist` with the given [path] and its relative `Channel`s */
    override fun remove( path: String ) {
        RemovePlaylistWorker.enqueue( path )
    }

    /** Update a `Playlist` with the given [path] and [name] */
    override fun update( path: String, name: String? ) {
        updatePlaylist( path, name )
    }
}