@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package studio.forface.freshtv.parsers.playlist

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import studio.forface.freshtv.domain.utils.EMPTY_STRING
import studio.forface.freshtv.parsers.SizedStream
import studio.forface.freshtv.parsers.utils.forEachLine

/**
 * @author Davide Giuseppe Farella.
 * An inline class that represents the content of a Playlist and expose [extractItems] for make a
 * first parsing on the content and split it in [ParsablePlaylistItem]s.
 */
internal class ParsablePlaylist( private val stream: SizedStream ) {

    private companion object {

        /** A [String] representing the header of the content */
        const val HEADER = "#EXTM3U"

        /** A [String] representing the footer of the content */
        const val FOOTER = "#EXT-X-ENDLIST"

        /** A [String] representing the head of a new item */
        const val CHANNEL_HEAD = "#EXTINF:-1"
    }

    /** Read the [stream] and send [ParsablePlaylistItem]s to the given [SendChannel] */
    suspend fun extractItems( items: SendChannel<ParsablePlaylistItem> ) = withContext( IO ) {
        stream.input.bufferedReader().use { reader ->
            var temp = EMPTY_STRING

            // If temp is not blank, create a ParsablePlaylistItem and offer to items' Channel
            fun sendItem() {
                if ( temp.isNotBlank() )
                    items.offer( ParsablePlaylistItem( temp.trim() ) )
            }

            // Filter blank lines and lines and lines that start "#EXTM3U" or "#EXT-X-ENDLIST"
            val filter: (String) -> Boolean = { it.isNotBlank() && ! it.startsWith( HEADER ) && ! it.startsWith( FOOTER ) }
            reader.forEachLine( filter ) { line ->

                // When a new channel is read
                if ( line.trimStart().startsWith( CHANNEL_HEAD ) ) {
                    // Send the previously item
                    sendItem()
                    // Then purge temp
                    temp = EMPTY_STRING
                }
                // Create a line removing "#EXTINF:-1" from prefix, if any, and appending a line break
                val newLine = line.removePrefix( CHANNEL_HEAD ) + "\n"
                // Append the new line to temp
                temp += newLine
            }

            // After the reading is complete, use the last cached item in temp, since there won't be a new line
            // starting with "#EXTINF:-1" that will declare the last channel has been completely read
            sendItem()
        }

        items.close()
    }
}