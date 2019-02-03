package studio.forface.freshtv.parsers.epg

import org.junit.Test

/**
 * @author Davide Giuseppe Farella
 * Test class for [ParsableEpgItem]
 */
internal class ParsableEpgItemTest {

    @Test // TODO
    fun test() {
        val channelContent = """
            <programme start="20190202065500 +0100" stop="20190202070000 +0100" channel="Rai Uno">
                <title lang="it">Gli imperdibili</title>
                <desc lang="it">Backstage, anteprime, eventi e curiosita' dentro lo schermo nel programma che offre informazione e approfondimento sulla programmazione dei canali che compongono il bouquet Rai.(n)</desc>
                <category lang="it">Mondi e culture</category>
                <rating>
                  <value>per tutti</value>
                </rating>
              </programme>
            """.trimIndent()

        val item = ParsableEpgItem( channelContent )() as ParsableEpgItem.Result.Guide
        println( item.content )
    }
}