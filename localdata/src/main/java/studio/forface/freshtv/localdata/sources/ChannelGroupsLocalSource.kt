package studio.forface.freshtv.localdata.sources

import kotlinx.coroutines.channels.ReceiveChannel
import studio.forface.freshtv.domain.entities.ChannelGroup.Type

/**
 * @author Davide Giuseppe Farella.
 * A source for Channel Groups stored locally
 */
interface ChannelGroupsLocalSource<Pojo> {

    /** @return all the stored [Pojo] of [Type.MOVIE] */
    fun allMovie(): List<Pojo>

    /** @return a [ReceiveChannel] of all the stored [Pojo] of [Type.MOVIE] */
    suspend fun observeAllMovie(): ReceiveChannel<List<Pojo>>

    /** @return all the stored [Pojo] of [Type.TV] */
    fun allTv(): List<Pojo>

    /** @return a [ReceiveChannel] of all the stored [Pojo] of [Type.TV] */
    suspend fun observeAllTv(): ReceiveChannel<List<Pojo>>

    /** Create a new [Pojo] */
    fun createChannelGroup( group: Pojo )

    /** Delete the stored [Pojo] with the given [id] */
    fun delete( id: String )

    /** Delete all the stored [Pojo]s */
    fun deleteAll()

    /** @return the [Pojo] with the given [id] */
    fun group( id: String ): Pojo

    /** Update an already stored [Pojo] */
    fun updateGroup( group: Pojo )
}