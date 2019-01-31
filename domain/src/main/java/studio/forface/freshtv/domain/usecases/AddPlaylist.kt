package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.Playlist
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella
 * A class for Add a Playlist from a Local or Remote Source.
 */
class AddPlaylist(
    private val localData: LocalData
) {

    /** Add [Playlist] with the given [path], [type] and optional [name] */
    operator fun invoke( path: String, type: Playlist.Type, name: String = path ) {
        val playList = Playlist( path, type, name)
        localData.storePlaylist( playList )
    }
}