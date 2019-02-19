package studio.forface.freshtv.localdata.room.dao

import androidx.room.*
import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.localdata.room.entities.TvGuidePojo

@Dao
interface TvGuideDao {

    @Insert
    fun insert( pojo: TvGuidePojo )

    @Update
    fun update( pojo: TvGuidePojo )

    @Delete
    fun delete( id: String )

    @Query("DELETE FROM Tv_guide" )
    fun deleteAll()

    @Query("DELETE FROM Tv_guide WHERE endTime < :dateTime" )
    fun deleteWithEndTimeLessThan( dateTime: LocalDateTime )

    @Query("SELECT * FROM Tv_guide WHERE id = :id LIMIT 1" )
    fun selectById( id: String ): TvGuidePojo

    @Query("SELECT * FROM Tv_guide WHERE channelId = :channelId AND ( startTime BETWEEN :from AND :to OR endTime BETWEEN :from AND :to OR :from BETWEEN startTime AND endTime OR :to BETWEEN startTime AND endTime )" )
    fun selectByChannelId(channelId: String, from: LocalDateTime, to: LocalDateTime ): List<TvGuidePojo>
}