package studio.forface.freshtv.localdata.sources

import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.domain.utils.LocalDateTimeHelper
import studio.forface.freshtv.localdata.TvGuidePojo
import studio.forface.freshtv.localdata.TvGuideQueries

/**
 * @author Davide Giuseppe Farella.
 * A source for Tv Guides stored locally
 */
class TvGuidesLocalSource( private val queries: TvGuideQueries) {

    /** Create a new guide [TvGuidePojo] */
    fun createGuide( guide: TvGuidePojo) {
        with( guide ) {
            queries.insert( id, channelId, programName, programDescription, startTime, endTime )
        }
    }

    /** Delete all the stored guides [TvGuidePojo] */
    fun deleteAll() {
        queries.deleteAll()
    }

    /** @return the [TvGuidePojo] with the given [id] */
    fun guide( id: Long ): TvGuidePojo = queries.selectById( id ).executeAsOne()

    /**
     * @return all the stored Guides [TvGuidePojo] with [TvGuidePojo.channelId] as the given [channelId]
     *
     * @param from the [LocalDateTime] representing the start of the time range to query.
     * Default if the [LocalDateTime] created from the Unix value of [Long.MIN_VALUE]
     *
     * @param to the [LocalDateTime] representing the end of the time range to query.
     * Default if the [LocalDateTime] created from the Unix value of [Long.MAX_VALUE]
     *
     * [from] and [to] will query all the [TvGuidePojo] which has [TvGuidePojo.startTime] or [TvGuidePojo.endTime]
     * in range of [from] and [to], or which [from] or [to] is in range of [TvGuidePojo.startTime] and
     * [TvGuidePojo.endTime]
     */
    fun guidesForChannel(channelId: String, from: LocalDateTime?, to: LocalDateTime? ): List<TvGuidePojo> {
        return queries.selectByChannelId(
            channelId,
            from = from ?: LocalDateTimeHelper.ofEpochSecond( Long.MIN_VALUE ),
            to = to ?: LocalDateTimeHelper.ofEpochSecond( Long.MAX_VALUE )
        ).executeAsList()
    }

    /** Update an already stored guide [TvGuidePojo] */
    fun updateGuide( guide: TvGuidePojo) {
        with( guide ) {
            queries.update( id, channelId, programName, programDescription, startTime, endTime )
        }
    }
}