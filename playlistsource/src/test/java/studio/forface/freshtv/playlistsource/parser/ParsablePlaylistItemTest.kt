package studio.forface.freshtv.playlistsource.parser

import org.junit.Test
import kotlin.system.measureNanoTime

/**
 * @author Davide Giuseppe Farella
 * Test class for [ParsablePlaylistItem]
 */
class ParsablePlaylistItemTest {

    private val longItem = """#EXTINF:-1 tvg-id="" group-title="PAY TV (Solo per guida)", tvg-logo="https://upload.wikimedia.org/wikipedia/it/thumb/b/b4/Sky3D-IT-channel-plinth-RGB-3.png/260px-Sky3D-IT-channel-plinth-RGB-3.png", Sky 3D
        |http://bit.ly/qdrforum
    """.trimMargin()

    private val noParamItem = """#EXTINF:-1,>>>>>SCIENZA<<<<<
        |http://bit.ly/qdrforum""".trimMargin()

    @Test
    fun performanceTest() {
        val item = ParsablePlaylistItem( longItem )
        val time = measureNanoTime { item("" ) }

        println( time / 100000 )
    }

    @Test
    fun `noParamItem parseCorrectly`() {
        val item = ParsablePlaylistItem( noParamItem )
        assert( item("") is ParsablePlaylistItem.Result.Channel )
    }
}