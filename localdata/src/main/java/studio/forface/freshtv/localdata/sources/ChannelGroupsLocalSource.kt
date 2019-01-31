package studio.forface.freshtv.localdata.sources

import studio.forface.freshtv.domain.entities.ChannelGroup.Type
import studio.forface.freshtv.localdata.ChannelGroupPojo
import studio.forface.freshtv.localdata.ChannelGroupQueries

/**
 * @author Davide Giuseppe Farella.
 * A source for Channel Groups stored locally
 */
class ChannelGroupsLocalSource( private val queries: ChannelGroupQueries) {

    /** @return all the stored [ChannelGroupPojo] of [Type.MOVIE] */
    fun allMovie(): List<ChannelGroupPojo> = queries.selectsMovie().executeAsList()

    /** @return all the stored [ChannelGroupPojo] of [Type.TV] */
    fun allTv(): List<ChannelGroupPojo> = queries.selectsTv().executeAsList()

    /** Create a new [ChannelGroupPojo] */
    fun createChannelGroup( group: ChannelGroupPojo) {
        with( group ) {
            queries.insert( id, name, type, imageUrl )
        }
    }

    /** Delete all the stored [ChannelGroupPojo] */
    fun deleteAll() {
        queries.deleteAll()
    }

    /** @return the [ChannelGroupPojo] with the given [id] */
    fun group( id: String ): ChannelGroupPojo = queries.selectById( id ).executeAsOne()

    /** Update an already stored [ChannelGroupPojo] */
    fun updateGroup( group: ChannelGroupPojo) {
        with( group ) {
            queries.update( id, name, imageUrl )
        }
    }
}