package studio.forface.freshtv.parsers.playlist

import studio.forface.freshtv.parsers.mockPlaylistContent
import kotlin.test.Test
import kotlin.test.assertNotNull

/**
 * @author Davide Giuseppe Farella
 * Test class for [ParsablePlaylist]
 */
internal class ParsablePlaylistTest {

    @Test
    fun extractItems() {
        val result = ParsablePlaylist( mockPlaylistContent ).extractItems()
        assertNotNull( result.first() )
        assert( result.isNotEmpty() )
    }
}