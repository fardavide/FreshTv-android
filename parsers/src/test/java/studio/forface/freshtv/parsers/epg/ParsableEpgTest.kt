package studio.forface.freshtv.parsers.epg

import studio.forface.freshtv.parsers.mockEpgContent
import kotlin.test.Test
import kotlin.test.assertNotNull

/**
 * @author Davide Giuseppe Farella
 * Test class for [ParsableEpg]
 */
class ParsableEpgTest {

    @Test
    fun extractItems() {
        val result = ParsableEpg( mockEpgContent ).extractItems()
        assertNotNull( result.first() )
        assert( result.isNotEmpty() )
    }
}