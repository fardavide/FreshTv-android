@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package studio.forface.freshtv.parsers.epg

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.withContext
import studio.forface.freshtv.domain.utils.EMPTY_STRING
import studio.forface.freshtv.parsers.utils.forEachLine
import java.io.InputStream

/**
 * @author Davide Giuseppe Farella.
 * An inline class that represents the content of an EPG and expose [extractItems] for make a first
 * parsing on the content and split it in [ParsableEpgItem]s.
 */
internal class ParsableEpg( private val stream: InputStream ) {

    private companion object {
        const val PROGRAM = "programme"
    }

    /** Read the [stream] and send [ParsableEpgItem]s to the given [SendChannel] */
    suspend fun extractItems( items: SendChannel<ParsableEpgItem> ) = withContext<Unit>( IO ) {
        stream.bufferedReader().use { reader ->
            var temp = EMPTY_STRING

            // Filter blank lines and lines and lines
            val filter: (String) -> Boolean = { it.isNotBlank() }
            reader.forEachLine( filter ) { line ->

                // When start read a programme, purge temp
                if ( line.trimStart().startsWith("<$PROGRAM" ) )
                    temp = EMPTY_STRING

                // Create a new line appending a line break
                val newLine = line + "\n"
                // Append newLine to temp
                temp += newLine

                // When finish read a programme, send it to items' channel
                if ( line.endsWith("</$PROGRAM>" ) )
                    items.offer( ParsableEpgItem( temp.trim() ) )
            }
        }

        items.close()
    }
}