package studio.forface.freshtv.domain.usecases

import kotlinx.coroutines.coroutineScope
import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.domain.entities.SourceFile.Epg
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

    /** A lambda that will be invoked when a progress is received */
    private var progressCallback: (Int) -> Unit = {}

    /** Set [progressCallback] for execute a lambda [block] when a progress is received */
    fun onProgress( block: (Int) -> Unit ) {
        progressCallback = block
    }

    /**
     * Refresh the playlists previously added
     * @return a [RefreshTvGuides.Error.Multi] of [Epg]s and [ParsingEpgError]s
     */
    suspend operator fun invoke() = localData.epgs()
        .map { invoke( it ) } // Invoke for every Playlist
        .let { Error.Multi( it ) } // Join all the RefreshTvGuides.Error.Single in a RefreshTvGuides.Error.Multi

    /**
     * Refresh the given [Epg]
     * @return a [RefreshChannels.Error.Single] of [Epg] and [ParsingEpgError]
     */
    suspend operator fun invoke( epg: SourceFile.Epg ) = coroutineScope {
        val errors = mutableListOf<ParsingEpgError>()
        parsers.readFrom(
            epg = epg,
            onTvGuide = { localData.storeTvGuide( it ) },
            onError = { errors += it },
            onProgress = { progressCallback( it ) }
        )
        Error.Single( epg, errors )
    }

    /**
     * Refresh the [Epg] with the given [epgPath]
     * @return a [List] of [ParsingEpgError]
     */
    suspend operator fun invoke( epgPath: String ) =
            this( localData.epg( epgPath ) )

    /** A sealed class for wrapping [ParsingEpgError]s */
    sealed class Error {
        /** @return whether error is present */
        abstract val hasError: Boolean

        /** A class representing a [ParsingEpgError]s for a single [Epg] */
        data class Single( val epg: Epg, val parsingErrors: List<ParsingEpgError> ) : Error() {
            override val hasError get() = parsingErrors.isNotEmpty()
        }

        /** A class representing a [ParsingEpgError]s for multiple [Epg]. It wraps a list of [Single] */
        data class Multi( val all: List<Single> ) : Error() {
            override val hasError get() = all.map { it.hasError }.reduce { acc, b -> acc || b }
        }
    }
}