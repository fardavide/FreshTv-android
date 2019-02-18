package studio.forface.freshtv.localdata.room.dao

import androidx.room.*
import studio.forface.freshtv.localdata.room.entities.MovieChannelPojo

@Dao
interface MovieChannelDao {

    @Insert
    fun insert( pojo: MovieChannelPojo )

    @Update
    fun update( pojo: MovieChannelPojo )

    @Delete
    fun delete( id: String )

    @Query("DELETE FROM Movie_channels" )
    fun deleteAll()

    @Query("SELECT count(*) FROM Movie_channels" )
    fun count(): Int

    @Query("SELECT * FROM Movie_channels ORDER BY favorite ASC, name ASC" )
    fun selectAll(): List<MovieChannelPojo>

    @Query("SELECT * FROM Movie_channels WHERE id = :id LIMIT 1" )
    fun selectById( id: String ): MovieChannelPojo

    @Query("SELECT * FROM Movie_channels WHERE groupName = :groupName ORDER BY favorite ASC, name ASC" )
    fun selectByGroup( groupName: String ): List<MovieChannelPojo>

    @Query("SELECT * FROM Movie_channels WHERE playlistPaths REGEXP '(^|, ) Escape(' + :path + ') (, |\$)' ORDER BY favorite ASC, name ASC" )
    fun selectByPlaylistPath( path: String ): List<MovieChannelPojo>
}