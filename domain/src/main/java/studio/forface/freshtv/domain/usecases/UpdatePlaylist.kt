package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.SourceFile.Playlist
import studio.forface.freshtv.domain.gateways.LocalData
import studio.forface.freshtv.domain.gateways.updatePlaylist

/**
 * @author Davide Giuseppe Farella
 * A class for update a [Playlist]
 */
class UpdatePlaylist( private val localData: LocalData ) {

    /** Change the [Playlist.path] and [Playlist.name] of the given [Playlist] */
    operator fun invoke( playlist: Playlist, path: String, name: String? ) {
        this( playlist.path, name ) // TODO
    }

    /** Change the [Playlist.path] and [Playlist.name] of the [Playlist] with the given [playlistPath] */
    operator fun invoke( playlistPath: String, name: String? ) {
        localData.updatePlaylist( playlistPath ) { it.copy( path = playlistPath, name = name ) }
    }
}