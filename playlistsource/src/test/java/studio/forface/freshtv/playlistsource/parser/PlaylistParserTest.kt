package studio.forface.freshtv.playlistsource.parser

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import studio.forface.freshtv.domain.entities.ChannelGroup
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.playlistsource.mockPlaylistContent
import kotlin.system.measureTimeMillis
import kotlin.test.Test

/**
 * @author Davide Giuseppe Farella.
 * Test class for [PlaylistParser]
 */
class PlaylistParserTest {

    @Test
    fun test() = runBlocking {
        val parser = PlaylistParser("", mockPlaylistContent )

        val channelsChannel = Channel<IChannel>()
        val groupsChannel = Channel<ChannelGroup>()
        val errorsChannel = Channel<PlaylistParser.ChannelError>()

        launch { for( channel in channelsChannel ) println( channel ) }
        launch { for( group in groupsChannel ) println( group ) }
        launch { for( error in errorsChannel ) println( error.reason.name ) }

        parser.run { this@runBlocking( channelsChannel, groupsChannel, errorsChannel ) }
        Unit
    }
}