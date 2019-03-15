@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package studio.forface.freshtv.parsers.epg

import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import java.io.InputStream

/**
 * @author Davide Giuseppe Farella.
 * An inline class that represents the content of an EPG and expose [extractItems] for make a first
 * parsing on the content and split it in [ParsableEpgItem]s.
 */
internal inline class ParsableEpg( private val stream: InputStream ) {

    private companion object {
        const val PROGRAM = "programme"
        const val TV = "tv"
    }

    /** @return a [List] of [ParsableEpgItem] extracted from the content of [ParsableEpg] */
    suspend fun extractItems( items: SendChannel<ParsableEpgItem> ) = coroutineScope<Unit> {
        stream.bufferedReader().use {
            // TODO
        }
        s
            .removeSuffix("</$TV>" )
            .split("<$PROGRAM" )
            .filter { it.contains("</$PROGRAM>" ) }
            .map { ParsableEpgItem("<$PROGRAM$it" ) }

        items.close()
    }
}