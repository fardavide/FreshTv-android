package studio.forface.freshtv.localdata.sources

import org.threeten.bp.LocalDateTime

/**
 * @author Davide Giuseppe Farella.
 * A source for Tv Guides stored locally
 */
interface TvGuidesLocalSource<Pojo> {

    /** Create a new guide [Pojo] */
    fun createGuide( guide: Pojo)

    /** Delete all the stored guides [Pojo] */
    fun deleteAll()

    /** Delete all the stored Guides [Pojo] with [Pojo]'s endTime less than the given [dateTime] */
    fun deleteGuidesBefore( dateTime: LocalDateTime )

    /** @return the [Pojo] with the given [id] */
    fun guide( id: String ): Pojo

    /**
     * @return the stored Guides [Pojo] with [Pojo]'s channelId as the given [channelId]
     *
     * @param time the [LocalDateTime] representing the the time range to query.
     * Default if the [LocalDateTime.now]
     *
     * This will query all the [Pojo] where [time] is between which has [Pojo]'s startTime and [Pojo]'s endTime
     */
    fun guideForChannel( channelId: String, time: LocalDateTime? ): Pojo

    /**
     * @return all the stored Guides [Pojo] with [Pojo]'s channelId as the given [channelId]
     *
     * @param from the [LocalDateTime] representing the start of the time range to query.
     * Default if the [LocalDateTime] created from the Unix value of [Long.MIN_VALUE]
     *
     * @param to the [LocalDateTime] representing the end of the time range to query.
     * Default if the [LocalDateTime] created from the Unix value of [Long.MAX_VALUE]
     *
     * [from] and [to] will query all the [Pojo] which has [Pojo]'s startTime or [Pojo]'s endTime in range of [from]
     * and [to], or which [from] or [to] is in range of [Pojo]'s startTime and [Pojo]'s endTime
     */
    fun guidesForChannelRanged( channelId: String, from: LocalDateTime?, to: LocalDateTime? ): List<Pojo>

    /** Update an already stored guide [Pojo] */
    fun updateGuide( guide: Pojo )
}