package studio.forface.freshtv.localdata.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import studio.forface.freshtv.localdata.room.entities.ChannelGroupPojo

@Dao
interface ChannelGroupDao {

    @Query("DELETE FROM Channel_groups" )
    fun deleteAll()

    @Insert
    fun insert( pojo: ChannelGroupPojo )

    @Update
    fun update( pojo: ChannelGroupPojo )

    @Query("SELECT * FROM Channel_groups" )
    fun selectAll(): List<ChannelGroupPojo>

    @Query("SELECT * FROM Channel_groups WHERE type = 'MOVIE'")
    fun selectMovies(): List<ChannelGroupPojo>

    @Query("SELECT * FROM Channel_groups WHERE type = 'TV'")
    fun selectTvs(): List<ChannelGroupPojo>

    @Query("SELECT * FROM Channel_groups WHERE id = :id LIMIT 1" )
    fun selectById( id: String ): ChannelGroupPojo
}