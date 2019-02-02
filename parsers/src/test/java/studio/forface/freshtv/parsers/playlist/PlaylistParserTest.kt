package studio.forface.freshtv.parsers.playlist

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import studio.forface.freshtv.domain.entities.ChannelGroup
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.errors.ParsingChannelError
import studio.forface.freshtv.parsers.mockPlaylistContent
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * @author Davide Giuseppe Farella.
 * Test class for [PlaylistParser]
 */
class PlaylistParserTest {

    @Test
    fun test() = runBlocking {
        val parser = PlaylistParser()

        val channelsChannel = Channel<IChannel>()
        val groupsChannel = Channel<ChannelGroup>()
        val errorsChannel = Channel<ParsingChannelError>()

        val channels = mutableListOf<IChannel>()
        val groups = mutableListOf<ChannelGroup>()
        val errors = mutableListOf<ParsingChannelError>()

        launch { for( channel in channelsChannel ) channels += channel }
        launch { for( group in groupsChannel ) groups += group }
        launch { for( error in errorsChannel ) errors += error }

        parser.parse("", mockPlaylistContent, channelsChannel, groupsChannel, errorsChannel  )

        println( channels )

        assert( channels.isNotEmpty() )
        assert( groups.isNotEmpty() )
        assert( errors.isNotEmpty() )

        assertEquals(channels.size + groups.size + errors.size, 64 )
    }
}