package studio.forface.freshtv.localdata.sources

import studio.forface.freshtv.domain.entities.ChannelGroup.Type

/**
 * @author Davide Giuseppe Farella.
 * A source for Channel Groups stored locally
 */
interface ChannelGroupsLocalSource<Pojo> {

    /** @return all the stored [Pojo] of [Type.MOVIE] */
    fun allMovie(): List<Pojo>

    /** @return all the stored [Pojo] of [Type.TV] */
    fun allTv(): List<Pojo>

    /** Create a new [Pojo] */
    fun createChannelGroup( group: Pojo)

    /** Delete all the stored [Pojo] */
    fun deleteAll()

    /** @return the [Pojo] with the given [id] */
    fun group( id: String ): Pojo

    /** Update an already stored [Pojo] */
    fun updateGroup( group: Pojo )
}