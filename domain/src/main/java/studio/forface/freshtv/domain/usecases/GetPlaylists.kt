package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.SourceFile.Playlist
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella
 * Get all the stored [Playlist]s
 */
class GetPlaylists( private val localData: LocalData ) {

    /** @return a [List] of [Playlist] */
    operator fun invoke() = localData.playlists()
}