package studio.forface.freshtv.localdata.room.sources

import studio.forface.freshtv.domain.entities.ChannelGroup.Type
import studio.forface.freshtv.localdata.room.dao.ChannelGroupDao
import studio.forface.freshtv.localdata.room.entities.ChannelGroupPojo
import studio.forface.freshtv.localdata.sources.ChannelGroupsLocalSource

/**
 * @author Davide Giuseppe Farella.
 * A source for Channel Groups stored locally
 */
class RoomChannelGroupsLocalSource(
    private val dao: ChannelGroupDao
) : ChannelGroupsLocalSource<ChannelGroupPojo> {

    /** @return all the stored [ChannelGroupPojo] of [Type.MOVIE] */
    override fun allMovie(): List<ChannelGroupPojo> = dao.selectMovies()

    /** @return all the stored [ChannelGroupPojo] of [Type.TV] */
    override fun allTv(): List<ChannelGroupPojo> = dao.selectTvs()

    /** Create a new [ChannelGroupPojo] */
    override fun createChannelGroup( group: ChannelGroupPojo ) {
        dao.insert( group )
    }

    /** Delete all the stored [ChannelGroupPojo] */
    override fun deleteAll() {
        dao.deleteAll()
    }

    /** @return the [ChannelGroupPojo] with the given [id] */
    override fun group( id: String ): ChannelGroupPojo = dao.selectById( id )

    /** Update an already stored [ChannelGroupPojo] */
    override fun updateGroup( group: ChannelGroupPojo) {
        dao.update( group )
    }
}