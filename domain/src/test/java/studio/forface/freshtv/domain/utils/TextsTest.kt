package studio.forface.freshtv.domain.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * @author Davide Giuseppe Farella
 * Test class for texts
 */
class TextsTest {

    @Test
    fun stringTo() {
        val stringInt = "4"
        assertEquals(4, stringInt.to() )
        assertEquals(4L, stringInt.to() )

        val stringNotInt = "abc"
        assertNull( stringNotInt.to<Int?>() )
        assertNull( stringNotInt.to<Long?>() )
    }
}