package studio.forface.freshtv.playlistsource

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
import studio.forface.freshtv.domain.errors.ParsingTvGuideError
import studio.forface.freshtv.domain.gateways.Parsers
import studio.forface.freshtv.playlistsource.playlist.PlaylistParser
import studio.forface.freshtv.playlistsource.playlist.invoke

/**
 * @author Davide Giuseppe Farella.
 * Implementation of [Parsers]
 */
internal class ParsersImpl(
    private val contentResolver: FileContentResolver = FileContentResolver(),
    private val parser: PlaylistParser = PlaylistParser()
): Parsers {

    /** Obtain [TvGuide]s and eventual [ParsingTvGuideError]s from the given [Epg] */
    override suspend fun readFrom(
        epg: SourceFile.Epg,
        onTvGuide: suspend (TvGuide) -> Unit,
        onError: suspend (ParsingTvGuideError) -> Unit
    ) = coroutineScope {
        val guidesChannel = Channel<TvGuide>()
        val errorsChannel = Channel<ParsingTvGuideError>()

        launch { for ( guide in guidesChannel ) onTvGuide( guide ) }
        launch { for( error in errorsChannel ) onError( error ) }

        TODO( "Parser not implemented" )
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

        parser { parse(
                playlist.path,
                contentResolver( playlist ),
                channelsChannel,
                groupsChannel,
                errorsChannel
        ) }
    }
}