package studio.forface.freshtv.localdata.room.sources

import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.domain.utils.LocalDateTimeHelper
import studio.forface.freshtv.localdata.room.dao.TvGuideDao
import studio.forface.freshtv.localdata.room.entities.TvGuidePojo
import studio.forface.freshtv.localdata.sources.TvGuidesLocalSource

/**
 * @author Davide Giuseppe Farella.
 * A source for Tv Guides stored locally
 */
class RoomTvGuidesLocalSource(
    private val dao: TvGuideDao
) : TvGuidesLocalSource<TvGuidePojo> {

    /** Create a new guide [TvGuidePojo] */
    override fun createGuide( guide: TvGuidePojo) {
        dao.insert( guide )
    }

    /** Delete all the stored guides [TvGuidePojo] */
    override fun deleteAll() {
        dao.deleteAll()
    }

    /** Delete all the stored Guides [TvGuidePojo] with [TvGuidePojo.endTime] less than the given [dateTime] */
    override fun deleteGuidesBefore( dateTime: LocalDateTime ) = dao.deleteWithEndTimeLessThan( dateTime )

    /** @return the [TvGuidePojo] with the given [id] */
    override fun guide( id: String ): TvGuidePojo = dao.selectById( id )

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
    override fun guidesForChannel( channelId: String, from: LocalDateTime?, to: LocalDateTime? ): List<TvGuidePojo> {
        return dao.selectByChannelId(
            channelId,
            from = from ?: LocalDateTimeHelper.ofEpochSecond( Long.MIN_VALUE ),
            to = to ?: LocalDateTimeHelper.ofEpochSecond( Long.MAX_VALUE )
        )
    }

    /** Update an already stored guide [TvGuidePojo] */
    override fun updateGuide( guide: TvGuidePojo) {
        dao.update( guide )
    }
}