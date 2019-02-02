package studio.forface.freshtv.domain.usecases

import kotlinx.coroutines.coroutineScope
import studio.forface.freshtv.domain.entities.SourceFile.Playlist
import studio.forface.freshtv.domain.errors.ParsingChannelError
import studio.forface.freshtv.domain.gateways.LocalData
import studio.forface.freshtv.domain.gateways.Parsers

/**
 * @author Davide Giuseppe Farella
 * A class for refresh the stored playlists
 */
class RefreshPlaylists(
    private val localData: LocalData,
    private val parsers: Parsers
) {

    /**
     * Refresh the playlists previously added
     * @return a [List] of [ParsingChannelError]
     */
    suspend operator fun invoke() = localData.playlists().flatMap { refreshOne( it ) }

    /**
     * Refresh the given [Playlist]
     * @return a [List] of [ParsingChannelError]
     */
    suspend fun refreshOne( playlist: Playlist ) = coroutineScope {
        val errors = mutableListOf<ParsingChannelError>()
        parsers.readFrom(
            playlist = playlist,
            onChannel = { localData.storeChannel( it ) },
            onGroup = { localData.storeGroup( it ) },
            onError = { errors += it }
        )
        errors
    }
}