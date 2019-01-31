package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.entities.Playlist
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella
 * A class for remove a saved [Playlist] and its relative Channels
 */
class RemovePlaylist( private val localData: LocalData ) {

    /** @see invoke */
    suspend operator fun invoke( playlist: Playlist ) {
        this( playlist.path )
    }

    /**
     * Delete the stored [Playlist] and remove it reference to relative [IChannel.playlistPaths].
     * Also delete the [IChannel] without any playlist path.
     */
    suspend operator fun invoke( playlistPath: String ) {
        localData.deletePlaylist( playlistPath )
        val channelsToEdit = localData.channelsWithPlaylist( playlistPath )
            .map { it.copyObj( playlistPaths = it.playlistPaths - playlistPath ) }
            .groupBy { it.playlistPaths.isEmpty() }

        // Delete channels without playlist paths
        channelsToEdit.getValue(true ).forEach { localData.deleteChannel( it.id ) }
        // Update others
        channelsToEdit.getValue(false ).forEach { localData.updateChannel( it ) }
    }
}