package studio.forface.freshtv.localdata.room.dao

import androidx.paging.DataSource
import androidx.room.*
import studio.forface.freshtv.localdata.room.entities.TvChannelPojo

@Dao
interface TvChannelDao {

    @Insert
    fun insert( pojo: TvChannelPojo )

    @Update
    fun update( pojo: TvChannelPojo )

    @Delete
    fun delete( id: String )

    @Query("DELETE FROM Tv_channel" )
    fun deleteAll()

    @Query("SELECT count(*) FROM Tv_channel" )
    fun count(): Int

    @Query("SELECT * FROM Tv_channel ORDER BY favorite ASC, name ASC" )
    fun selectAll(): List<TvChannelPojo>

    @Query("SELECT * FROM Tv_channel ORDER BY favorite ASC, name ASC" )
    fun selectAllPaged(): DataSource.Factory<Int, TvChannelPojo>

    @Query("SELECT * FROM Tv_channel WHERE id = :id LIMIT 1" )
    fun selectById( id: String ): TvChannelPojo

    @Query("SELECT * FROM Tv_channel WHERE groupName = :groupName ORDER BY favorite ASC, name ASC" )
    fun selectByGroup( groupName: String ): List<TvChannelPojo>

    @Query("SELECT * FROM Tv_channel WHERE groupName = :groupName ORDER BY favorite ASC, name ASC" )
    fun selectByGroupPaged( groupName: String ): DataSource.Factory<Int, TvChannelPojo>

    @Query("SELECT * FROM Tv_channel WHERE playlistPaths REGEXP '(^|, ) Escape(' + :path + ') (, |\$)' ORDER BY favorite ASC, name ASC" )
    fun selectByPlaylistPath( path: String ): List<TvChannelPojo>

    @Query("SELECT * FROM Tv_channel WHERE playlistPaths REGEXP '(^|, ) Escape(' + :path + ') (, |\$)' ORDER BY favorite ASC, name ASC" )
    fun selectByPlaylistPathPaged( path: String ): DataSource.Factory<Int, TvChannelPojo>
}