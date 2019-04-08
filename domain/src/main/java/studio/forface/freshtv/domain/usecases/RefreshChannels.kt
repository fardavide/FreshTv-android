package studio.forface.freshtv.domain.usecases

import kotlinx.coroutines.coroutineScope
import studio.forface.freshtv.domain.entities.SourceFile.Playlist
import studio.forface.freshtv.domain.errors.ParsingChannelError
import studio.forface.freshtv.domain.gateways.LocalData
import studio.forface.freshtv.domain.gateways.Parsers
import studio.forface.freshtv.domain.utils.reduceOrDefault

/**
 * A class for refresh the stored playlists
 *
 * @author Davide Giuseppe Farella
 */
class RefreshChannels(
    private val localData: LocalData,
    private val parsers: Parsers
) {

    /**
     * Refresh the playlists previously added
     *
     * @return a [RefreshChannels.Error.Multi] of [Playlist]s and [ParsingChannelError]s
     *
     * @throws RefreshChannels.FatalException
     */
    suspend operator fun invoke() = localData.playlists()
        .map { invoke( it ) } // Invoke for every Playlist
        .let { Error.Multi( it ) } // Join all the RefreshChannels.Error.Single in a RefreshChannels.Error.Multi

    /**
     * Refresh the given [Playlist]
     *
     * @return a [RefreshChannels.Error.Single] of [Playlist] and [ParsingChannelError]s
     *
     * @throws RefreshChannels.FatalException
     */
    suspend operator fun invoke( playlist: Playlist ) = coroutineScope {
        val errors = mutableListOf<ParsingChannelError>()
        try {
            parsers.readFrom(
                playlist = playlist,
                onChannel = { localData.storeChannel( it ) },
                onGroup = { localData.storeGroup( it ) },
                onError = { errors += it }
            )
            Error.Single( playlist, errors )
        } catch ( t: Throwable ) {
            throw FatalException( playlist, t )
        }
    }

    /**
     * Refresh the [Playlist] with the given [playlistPath]
     *
     * @return a [List] of [ParsingChannelError]
     *
     * @throws RefreshChannels.FatalException
     */
    suspend operator fun invoke( playlistPath: String ) =
            this( localData.playlist( playlistPath ) )


    /** An exception that contains the [Playlist] that causes the failure */
    class FatalException( val playlist: Playlist, cause: Throwable ): Exception( cause )

    /** A sealed class for wrapping [ParsingChannelError]s */
    sealed class Error {
        /** @return whether error is present */
        abstract val hasError: Boolean

        /** A class representing a [ParsingChannelError]s for a single [Playlist] */
        data class Single( val playlist: Playlist, val parsingErrors: List<ParsingChannelError> ) : Error() {
            override val hasError get() = parsingErrors.isNotEmpty()
        }

        /** A class representing a [ParsingChannelError]s for multiple [Playlist]. It wraps a list of [Single] */
        data class Multi( val all: List<Single> ) : Error() {
            override val hasError get() = all.map { it.hasError }
                    .reduceOrDefault(false ) { acc, b -> acc || b }
        }
    }
}