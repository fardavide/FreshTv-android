package studio.forface.freshtv.playlistsource.parser

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import studio.forface.freshtv.domain.entities.ChannelGroup
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.errors.ParsingChannelError
import studio.forface.freshtv.playlistsource.mockPlaylistContent
import kotlin.test.Test

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

        runBlocking {
            parser { parse("", mockPlaylistContent, channelsChannel, groupsChannel, errorsChannel) }
        }

        assert( channels.isNotEmpty() )
        assert( groups.isNotEmpty() )
        assert( errors.isNotEmpty() )
    }
}