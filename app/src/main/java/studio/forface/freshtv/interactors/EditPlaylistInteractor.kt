package studio.forface.freshtv.interactors

import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.domain.usecases.AddPlaylist
import studio.forface.freshtv.domain.usecases.UpdatePlaylist
import studio.forface.freshtv.services.RemovePlaylistWorker

/**
 * @author Davide Giuseppe Farella
 * An Interactor for Add or Update a `Playlist`
 *
 * Inherit from [AbsEditSourceFileInteractor]
 */
internal class EditPlaylistInteractor(
        private val addPlaylist: AddPlaylist,
        private val updatePlaylist: UpdatePlaylist
) : AbsEditSourceFileInteractor {

    /** Add a `Playlist` with the given [path], [type] and [name] */
    override fun add( path: String, type: SourceFile.Type, name: String? ) {
        addPlaylist( path, type,name ?: path )
    }

    /** Remove the `Playlist` with the given [path] and its relative `Channel`s */
    override fun remove( path: String ) {
        RemovePlaylistWorker.enqueue( path )
    }

    /** Add a `Playlist` with the given [path] and [name] */
    override fun update( path: String, name: String? ) {
        updatePlaylist( path, name )
    }
}