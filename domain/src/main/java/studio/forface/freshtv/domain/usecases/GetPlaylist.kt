package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.SourceFile.Playlist
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella
 * Get a [Playlist]
 */
class GetPlaylist( private val localData: LocalData ) {

    /**
     * @return a [Playlist]
     * @param playlistPath the [Playlist.path]
     */
    operator fun invoke( playlistPath: String ) = localData.playlist( playlistPath )
}