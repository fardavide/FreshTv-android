@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package studio.forface.freshtv.parsers.epg

/**
 * @author Davide Giuseppe Farella.
 * An inline class that represents the content of an EPG and expose [extractItems] for make a first
 * parsing on the content and split it in [ParsableEpgItem]s.
 */
internal inline class ParsableStringEpg(private val s: String ) {

    private companion object {
        const val PROGRAM = "programme"
        const val TV = "tv"
    }

    /** @return a [List] of [ParsableEpgItem] extracted from the content of [ParsableStringEpg] */
    fun extractItems() : List<ParsableEpgItem> = s.removeSuffix("</$TV>" )
            .split("<$PROGRAM" )
            .filter { it.contains("</$PROGRAM>" ) }
            .map { ParsableEpgItem("<$PROGRAM$it" ) }
}