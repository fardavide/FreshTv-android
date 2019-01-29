package studio.forface.freshtv.playlistsource.parser

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import studio.forface.freshtv.domain.entities.ChannelGroup
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

        val groupsChannel = Channel<ChannelGroup>()
        val errorsChannel = Channel<PlaylistParser.ChannelError>()

        launch { groupsChannel.consumeEach { println( it ) } }
        launch { errorsChannel.consumeEach { println( it.reason.name ) } }

        val time = measureTimeMillis {
            launch {
                parser.run { this@launch( Channel(), groupsChannel, errorsChannel ) }
            }
        }
        println( time )
    }
}