package studio.forface.freshtv.domain.usecases

import kotlinx.coroutines.coroutineScope
import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.domain.entities.SourceFile.Playlist
import studio.forface.freshtv.domain.errors.ParsingEpgError
import studio.forface.freshtv.domain.gateways.LocalData
import studio.forface.freshtv.domain.gateways.Parsers

/**
 * @author Davide Giuseppe Farella
 * A class for refresh the stored epg
 */
class RefreshTvGuides(
    private val localData: LocalData,
    private val parsers: Parsers
) {

    /**
     * Refresh the playlists previously added
     * @return a [List] of [ParsingEpgError]
     */
    suspend operator fun invoke() = localData.epgs().flatMap { refreshOne( it ) }

    /**
     * Refresh the given [Playlist]
     * @return a [List] of [ParsingEpgError]
     */
    suspend fun refreshOne( epg: SourceFile.Epg ) = coroutineScope {
        val errors = mutableListOf<ParsingEpgError>()
        parsers.readFrom(
            epg = epg,
            onTvGuide = { localData.storeTvGuide( it ) },
            onError = { errors += it }
        )
        errors
    }
}