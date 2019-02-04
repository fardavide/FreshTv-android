package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.entities.SourceFile.Epg
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella
 * A class for remove a saved [Epg]
 */
class RemoveEpg( private val localData: LocalData ) {

    /** @see invoke */
    suspend operator fun invoke( epg: Epg ) {
        this( epg.path )
    }

    /**
     * Delete the stored [Epg] and remove it reference to relative [IChannel.playlistPaths].
     * Also delete the [IChannel] without any playlist path.
     */
    suspend operator fun invoke( epgPath: String ) {
        localData.deleteEpg( epgPath )
    }
}