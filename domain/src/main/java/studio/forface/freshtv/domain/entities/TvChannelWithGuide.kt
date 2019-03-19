package studio.forface.freshtv.domain.entities

import org.threeten.bp.LocalDateTime

/**
 * @author Davide Giuseppe Farella.
 * A type of Channel for TV.
 *
 * Inherit from [IChannel]
 */
data class TvChannelWithGuide(
    val id: String,
    val name: String,
    val imageUrl: Url? = null,
    val favorite: Boolean = false,
    val program: Program? = null
) {
    /** A Program of TvGuide for Channel */
    data class Program(
        val title: String,
        val startTime: LocalDateTime,
        val endTime: LocalDateTime
    )
}