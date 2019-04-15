package studio.forface.freshtv.parsers.epg

import studio.forface.freshtv.parsers.mockEpgContent
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * @author Davide Giuseppe Farella
 * Test class for [ParsableStringEpg]
 */
class ParsableEpgTest {

    @Test
    fun `extractItems returnsRightSize`() {
        val result = ParsableStringEpg( mockEpgContent ).extractItems()
        assertEquals(3, result.size )
    }

    @Test
    fun `extractItems hasRightIds`() {
        val firstResult = ParsableStringEpg( mockEpgContent )
            .extractItems()
            .first()
            .run { invoke() as ParsableEpgItem.Result.Guide }
            .run { content }
        assertEquals("Rai Uno", firstResult.channelId )
    }
}