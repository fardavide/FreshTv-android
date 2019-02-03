package studio.forface.freshtv.parsers

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import studio.forface.freshtv.domain.entities.ChannelGroup
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.domain.entities.SourceFile.Epg
import studio.forface.freshtv.domain.entities.SourceFile.Playlist
import studio.forface.freshtv.domain.entities.TvGuide
import studio.forface.freshtv.domain.errors.ParsingChannelError
import studio.forface.freshtv.domain.errors.ParsingEpgError
import studio.forface.freshtv.domain.gateways.Parsers
import studio.forface.freshtv.parsers.epg.EpgParser
import studio.forface.freshtv.parsers.playlist.PlaylistParser

/**
 * @author Davide Giuseppe Farella.
 * Implementation of [Parsers]
 */
internal class ParsersImpl(
    private val contentResolver: FileContentResolver = FileContentResolver(),
    private val epgParser: EpgParser = EpgParser(),
    private val playlistParser: PlaylistParser = PlaylistParser()
): Parsers {

    /** Obtain [TvGuide]s and eventual [ParsingEpgError]s from the given [Epg] */
    override suspend fun readFrom(
        epg: SourceFile.Epg,
        onTvGuide: suspend (TvGuide) -> Unit,
        onError: suspend (ParsingEpgError) -> Unit
    ) = coroutineScope {
        val guidesChannel = Channel<TvGuide>()
        val errorsChannel = Channel<ParsingEpgError>()

        launch { for ( guide in guidesChannel ) onTvGuide( guide ) }
        launch { for( error in errorsChannel ) onError( error ) }

        epgParser( contentResolver( epg ), guidesChannel, errorsChannel )
    }

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

        playlistParser(
                playlist.path,
                contentResolver( playlist ),
                channelsChannel,
                groupsChannel,
                errorsChannel
        )
    }
}