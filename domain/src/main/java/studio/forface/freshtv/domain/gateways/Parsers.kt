package studio.forface.freshtv.domain.gateways

import studio.forface.freshtv.domain.entities.ChannelGroup
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.entities.SourceFile.Epg
import studio.forface.freshtv.domain.entities.SourceFile.Playlist
import studio.forface.freshtv.domain.entities.TvGuide
import studio.forface.freshtv.domain.errors.ParsingChannelError
import studio.forface.freshtv.domain.errors.ParsingEpgError
import studio.forface.freshtv.domain.utils.Invokable
import java.io.InputStream

/**
 * A source for File Sources
 * Implements [Invokable]
 *
 * @author Davide Giuseppe Farella
 */
interface Parsers: Invokable {

    /** Obtain [TvGuide]s and eventual [ParsingEpgError]s from the given [Epg] */
    suspend fun readFrom(
        epg: Epg,
        onTvGuide: suspend (TvGuide) -> Unit = {},
        onError: suspend (ParsingEpgError) -> Unit = {},
        onProgress: (Int) -> Unit = {}
    )

    /** Obtain [IChannel]s, [ChannelGroup]s and eventual [ParsingChannelError]s from the given [Playlist] */
    suspend fun readFrom(
        playlist: Playlist,
        onChannel: suspend (IChannel) -> Unit = {},
        onGroup: suspend (ChannelGroup) -> Unit = {},
        onError: suspend (ParsingChannelError) -> Unit = {}
    )
}

/** An interface for retrieve an [InputStream] from a [String] representing an `Uri` */
interface UriResolver {
    /** @return an [InputStream] from a [String] representing an `Uri` */
    operator fun invoke( stringUri: String ): InputStream
}