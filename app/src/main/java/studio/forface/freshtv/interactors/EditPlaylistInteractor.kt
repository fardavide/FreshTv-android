package studio.forface.freshtv.interactors

import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.domain.usecases.AddPlaylist
import studio.forface.freshtv.domain.usecases.UpdatePlaylist

/**
 * @author Davide Giuseppe Farella
 * An Interactor for Add or Update a `Playlist`
 */
internal class EditPlaylistInteractor(
        private val addPlaylist: AddPlaylist,
        private val updatePlaylist: UpdatePlaylist
) {
    /** Add a `Playlist` with the given [path], [type] and [name] */
    fun add( path: String, type: SourceFile.Type, name: String? ) {
        addPlaylist( path, type,name ?: path )
    }

    /** Add a `Playlist` with the given [path] and [name] */
    fun update( path: String, name: String? ) {
        updatePlaylist( path, name )
    }
}