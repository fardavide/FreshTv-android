package studio.forface.freshtv.entities

import com.soywiz.klock.DateTime

/**
 * @author Davide Giuseppe Farella
 * An entity representing a Guide entity for a Tv Channel
 */
data class TvGuide (

    /** The [String] id of the Channel */
    val id: Long,

    /** The [String] id of the related Tv Channel */
    val channelId: String,

    /** The [String] name of the Tv Program */
    val programName: String,

    /** The [String] description of the Tv Program */
    val programDescription: String,

    /** A [DateTime] representing the start time of the Tv Program */
    val startTime: DateTime,

    /** A [DateTime] representing the end time of the Tv Program */
    val endTime: DateTime

) {

    /**
     * An operator function for merge 2 [TvGuide] entities. e.g. `guide1 + guide2`
     * @return this [TvGuide] merged with the given [newGuide]
     */
    operator fun plus( newGuide: TvGuide ) = copy(
        programName =           newGuide.programName,
        programDescription =    newGuide.programDescription,
        startTime =             newGuide.startTime,
        endTime =               newGuide.endTime
    )
}