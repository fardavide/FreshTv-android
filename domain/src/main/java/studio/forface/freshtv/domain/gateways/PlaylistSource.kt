package studio.forface.freshtv.domain.gateways

import studio.forface.freshtv.domain.entities.ChannelGroup
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.entities.Playlist
import studio.forface.freshtv.domain.errors.ParsingChannelError

/**
 * @author Davide Giuseppe Farella
 * A source for Playlists
 */
interface PlaylistSource {

    /** Obtain [IChannel]s, [ChannelGroup]s and eventual [ParsingChannelError]s from the given [Playlist] */
    suspend fun readFrom(
            playlist: Playlist,
            onChannel: suspend (IChannel) -> Unit,
            onGroup: suspend (ChannelGroup) -> Unit,
            onError: suspend (ParsingChannelError) -> Unit
    )
}

/**
 * An invoke function for execute a [block] within a [PlaylistSource]
 * @return [T]
 */
operator fun <T> PlaylistSource.invoke( block: PlaylistSource.() -> T ) = block()