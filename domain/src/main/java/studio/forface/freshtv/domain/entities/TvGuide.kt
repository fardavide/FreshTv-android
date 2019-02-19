package studio.forface.freshtv.domain.entities

import org.threeten.bp.LocalDateTime

/**
 * @author Davide Giuseppe Farella
 * An entity representing a Guide entity for a Tv Channel
 */
data class TvGuide (

        /** The [String] id of the Tv Guide */
        val id: String,

        /** The [String] id of the related Channel */
        val channelId: String,

        /** The [String] title of the Program */
        val title: String,

        /** The [String] description of the Program */
        val description: String,

        /** An OPTIONAL [String] url for the image of the Program */
        val imageUrl: Url?,

        /** A [String] category for the image of the Program */
        val category: String?,

        /** An OPTIONAL [Int] year of the Program */
        val year: Int?,

        /** An OPTIONAL [String] country of the Program */
        val country: String?,

        /** An OPTIONAL [Credits] for the Program */
        val credits: Credits?,

        /** A [String] rating for the Program */
        val rating: String?,

        /** A [LocalDateTime] representing the start time of the Program */
        val startTime: LocalDateTime,

        /** A [LocalDateTime] representing the end time of the Program */
        val endTime: LocalDateTime
) {

    /**
     * An operator function for merge 2 [TvGuide] entities. e.g. `guide1 + guide2`
     * @return this [TvGuide] merged with the given [newGuide]
     */
    operator fun plus( newGuide: TvGuide ) = copy(
            title =         newGuide.title,
            description =   newGuide.description,
            imageUrl =      newGuide.imageUrl ?: imageUrl,
            category =      newGuide.category,
            year =          newGuide.year ?: year,
            country =       newGuide.country ?: country,
            credits =       newGuide.credits ?: credits,
            rating =        newGuide.rating,
            startTime =     newGuide.startTime,
            endTime =       newGuide.endTime
    )

    /** A class containing credits for the [TvGuide] */
    data class Credits(
            val director: String?,
            val actors: List<String>
    )
}