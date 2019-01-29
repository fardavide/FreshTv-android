@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package studio.forface.freshtv.playlistsource.parser

import kotlinx.coroutines.coroutineScope

/**
 * @author Davide Giuseppe Farella.
 * An inline class that represents the content of a Playlist and expose [extractItems] for make a first parsing on the
 * content and split it in [ParsableItem]s.
 */
internal inline class ParsablePlaylist( private val s: String ) {

    private companion object {

        /** A [String] representing the header of the content */
        const val HEADER = "#EXTM3U"

        /** A [String] representing the footer of the content */
        const val FOOTER = "#EXT-X-ENDLIST"

        /** A [String] representing the head of a new item */
        const val CHANNEL_HEAD = "#EXTINF:-1"
    }

    /** @return a [List] of [ParsableItem] extracted from the content of [ParsablePlaylist] */
    suspend fun extractItems() : List<ParsableItem> = coroutineScope {
        s.removePrefix( HEADER ).removeSuffix( FOOTER )
            .split( CHANNEL_HEAD )
            .map { ParsableItem( it.trim() ) }
            .toList()
    }
}