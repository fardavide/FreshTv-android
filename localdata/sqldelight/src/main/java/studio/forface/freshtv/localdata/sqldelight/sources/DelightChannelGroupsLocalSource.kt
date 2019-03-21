package studio.forface.freshtv.localdata.sqldelight.sources

import studio.forface.freshtv.domain.entities.ChannelGroup.Type
import studio.forface.freshtv.localdata.sources.ChannelGroupsLocalSource
import studio.forface.freshtv.localdata.sqldelight.ChannelGroupPojo
import studio.forface.freshtv.localdata.sqldelight.ChannelGroupQueries

/**
 * @author Davide Giuseppe Farella.
 * A source for Channel Groups stored locally
 */
class DelightChannelGroupsLocalSource( 
    private val queries: ChannelGroupQueries
): ChannelGroupsLocalSource<ChannelGroupPojo> {

    /** @return all the stored [ChannelGroupPojo] of [Type.MOVIE] */
    override fun allMovie() = queries.selectMovie().executeAsList()

    /** @return all the stored [ChannelGroupPojo] of [Type.TV] */
    override fun allTv() = queries.selectTv().executeAsList()

    /** Create a new [ChannelGroupPojo] */
    override fun createChannelGroup( group: ChannelGroupPojo) {
        with( group ) {
            queries.insert( id, name, type, imageUrl )
        }
    }

    /** Delete the stored [ChannelGroupPojo] with the given [id] */
    override fun delete( id: String ) {
        queries.deleteById( id )
    }

    /** Delete all the stored [ChannelGroupPojo] */
    override fun deleteAll() {
        queries.deleteAll()
    }

    /** @return the [ChannelGroupPojo] with the given [id] */
    override fun group( id: String ) = queries.selectById( id ).executeAsOne()

    /** Update an already stored [ChannelGroupPojo] */
    override fun updateGroup( group: ChannelGroupPojo) {
        with( group ) {
            queries.update( id, name, imageUrl )
        }
    }
}