package studio.forface.freshtv.localdata

import com.squareup.sqldelight.ColumnAdapter
import org.junit.Test
import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.localdata.sqldelight.DateTimeColumnAdapter
import studio.forface.freshtv.localdata.sqldelight.StringIntMapColumnAdapter
import studio.forface.freshtv.localdata.sqldelight.StringListColumnAdapter
import kotlin.test.assertEquals

/**
 * @author Davide Giuseppe Farella
 * Test class for [ColumnAdapter]s
 */
internal class ColumnAdapterTest {

    @Test
    fun dateTime() {
        val dt = LocalDateTime.now()
        val encode = DateTimeColumnAdapter.encode( dt )
        val decode = DateTimeColumnAdapter.decode( encode )

        assertEquals( dt, decode )
    }

    @Test
    fun stringIntMap() {
        val map = mapOf(
            "hello" to 15,
            "bye-bye" to 645245,
            "nothing$%ˆˆ65" to -234
        )
        val encode = StringIntMapColumnAdapter.encode( map )
        val decode = StringIntMapColumnAdapter.decode( encode )

        assertEquals( map, decode )
    }

    @Test
    fun stringList() {
        val list = listOf( "hello", "bye bye my dear friend", "$(*&#ˆˆ" )
        val encode = StringListColumnAdapter.encode( list )
        val decode = StringListColumnAdapter.decode( encode )

        assertEquals( list, decode )
    }
}