@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package studio.forface.freshtv.parsers.epg

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.withContext
import studio.forface.freshtv.domain.utils.EMPTY_STRING
import studio.forface.freshtv.parsers.SizedStream
import studio.forface.freshtv.parsers.utils.forEachLine
import studio.forface.freshtv.parsers.utils.forEachLineSized

/**
 * @author Davide Giuseppe Farella.
 * An inline class that represents the content of an EPG and expose [extractItems] for make a first
 * parsing on the content and split it in [ParsableEpgItem]s.
 */
internal class ParsableEpg( private val stream: SizedStream ) {

    private companion object {
        const val PROGRAM = "programme"
    }

    /** Read the [stream] and send [ParsableEpgItem]s to the given [SendChannel] */
    suspend fun extractItems(
        items: SendChannel<ParsableEpgItem>,
        progress: SendChannel<Int>? = null
    ) = withContext<Unit>( IO ) {
        stream.input.bufferedReader().use { reader ->
            val total = stream.size
            var temp = EMPTY_STRING

            // Filter blank lines and lines and lines
            var count = 0
            val filter: (String) -> Boolean = { it.isNotBlank() }
            reader.forEachLine( filter ) { line ->
                // total : current = 100 : X
                // val percentage = ( readSize * 100 / total ).toInt() TODO progress
                // progress?.offer( percentage )

                // count++
                // if ( count % 5000 == 0 )
                //     println( "total: $total - current: $readSize - progress: $percentage" )

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
        progress?.close()
    }
}