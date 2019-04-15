package studio.forface.freshtv.localdata.sqldelight.sources

import com.squareup.sqldelight.Query
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.map
import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.domain.utils.LocalDateTimeHelper
import studio.forface.freshtv.localdata.sources.TvGuidesLocalSource
import studio.forface.freshtv.localdata.sqldelight.TvGuidePojo
import studio.forface.freshtv.localdata.sqldelight.TvGuideQueries
import studio.forface.freshtv.localdata.sqldelight.utils.asChannel
import studio.forface.freshtv.localdata.sqldelight.utils.mapToOne

/**
 * @author Davide Giuseppe Farella.
 * A source for Tv Guides stored locally
 */
class DelightTvGuidesLocalSource(
    private val queries: TvGuideQueries
) : TvGuidesLocalSource<TvGuidePojo> {

    /** @return all the stored Guides [TvGuidePojo] */
    override fun all(): List<TvGuidePojo> = queries.selectAll().executeAsList()

    /** @return the [Int] count of the stored channels [TvGuidePojo] */
    override fun count(): Int = queries.count().executeAsOne().toInt()

    /**
     * @return [ReceiveChannel] of the [Int] count of the stored channels [TvGuidePojo]
     * Maps the [Query] into a [ReceiveChannel]
     */
    override suspend fun observeCount() = queries.count().asChannel().mapToOne().map { it.toInt() }

    /** Create a new guide [TvGuidePojo] */
    override fun createGuide( guide: TvGuidePojo ) {
        with( guide ) {
            queries.insert(
                    id =                id,
                    channelId =         channelId,
                    title =             title,
                    description =       description,
                    imageUrl =          imageUrl,
                    category =          category,
                    year =              year,
                    country =           country,
                    credits_director =  credits_director,
                    credits_actors =    credits_actors,
                    rating =            rating,
                    startTime =         startTime,
                    endTime =           endTime
            )
        }
    }

    /** Delete all the stored guides [TvGuidePojo] */
    override fun deleteAll() {
        queries.deleteAll()
    }

    /** Delete all the stored Guides [TvGuidePojo] with [TvGuidePojo.endTime] less than the given [dateTime] */
    override fun deleteGuidesBefore( dateTime: LocalDateTime ) = queries.deleteWithEndTimeLessThan( dateTime )

    /** @return the [TvGuidePojo] with the given [id] */
    override fun guide( id: String ): TvGuidePojo = queries.selectById( id ).executeAsOne()

    /**
     * @return the stored Guides [TvGuidesLocalSource] with [TvGuidesLocalSource]'s channelId as the given
     * [channelId]
     *
     * @param time the [LocalDateTime] representing the the time range to query.
     * Default if the [LocalDateTime.now]
     *
     * This will query all the [TvGuidePojo] where [time] is between which has [TvGuidePojo.startTime] and
     * [TvGuidePojo.endTime]
     */
    override fun guideForChannel( channelId: String, time: LocalDateTime? ) =
            queries.selectFirstByChannelId( channelId, time ?: LocalDateTime.now() ).executeAsOne()

    /** @return all the stored Guides [TvGuidePojo] with [TvGuidePojo.channelId] as the given [channelId] */
    override fun guidesForChannel( channelId: String ): List<TvGuidePojo> {
        return queries.selectByChannelId( channelId ).executeAsList()
    }

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
    override fun guidesForChannelRanged(
        channelId: String,
        from: LocalDateTime?,
        to: LocalDateTime?
    ): List<TvGuidePojo> {
        return queries.selectByChannelIdRanged(
            channelId,
            from = from ?: LocalDateTimeHelper.ofEpochSecond( Long.MIN_VALUE ),
            to = to ?: LocalDateTimeHelper.ofEpochSecond( Long.MAX_VALUE )
        ).executeAsList()
    }



    /** Update an already stored guide [TvGuidePojo] */
    override fun updateGuide( guide: TvGuidePojo) {
        with( guide ) {
            queries.update(
                    id =                id,
                    channelId =         channelId,
                    title =             title,
                    description =       description,
                    imageUrl =          imageUrl,
                    category =          category,
                    year =              year,
                    country =           country,
                    credits_director =  credits_director,
                    credits_actors =    credits_actors,
                    rating =            rating,
                    startTime =         startTime,
                    endTime =           endTime
            )
        }
    }
}