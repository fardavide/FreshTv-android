package studio.forface.freshtv.playlistsource

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import studio.forface.freshtv.domain.entities.ChannelGroup
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.entities.Playlist
import studio.forface.freshtv.domain.errors.ParsingChannelError
import studio.forface.freshtv.domain.gateways.PlaylistSource
import studio.forface.freshtv.playlistsource.parser.PlaylistParser
import studio.forface.freshtv.playlistsource.parser.invoke

/**
 * @author Davide Giuseppe Farella.
 * Implementation of [PlaylistSource]
 */
internal class PlaylistSourceImpl(
    private val contentResolver: FileContentResolver = FileContentResolver(),
    private val parser: PlaylistParser = PlaylistParser()
): PlaylistSource {

    /** Obtain [IChannel]s, [ChannelGroup]s and eventual [ParsingChannelError]s from the given [Playlist] */
    override suspend fun readFrom(
            playlist: Playlist,
            onChannel: suspend (IChannel) -> Unit,
            onGroup: suspend (ChannelGroup) -> Unit,
            onError: suspend (ParsingChannelError) -> Unit
    ) = coroutineScope {
        val channelsChannel = Channel<IChannel>()
        val groupsChannel = Channel<ChannelGroup>()
        val errorsChannel = Channel<ParsingChannelError>()

        launch { for( channel in channelsChannel ) onChannel( channel ) }
        launch { for( group in groupsChannel ) onGroup( group ) }
        launch { for( error in errorsChannel ) onError( error ) }

        parser { parse(
                playlist.path,
                contentResolver( playlist ),
                channelsChannel,
                groupsChannel,
                errorsChannel
        ) }
    }
}