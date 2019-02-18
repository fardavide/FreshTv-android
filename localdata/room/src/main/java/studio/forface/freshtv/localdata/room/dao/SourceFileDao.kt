package studio.forface.freshtv.localdata.room.dao

import androidx.room.*
import studio.forface.freshtv.localdata.room.entities.SourceFilePojo

@Dao
interface SourceFileDao {

    @Insert
    fun insert( pojo: SourceFilePojo )

    @Update
    fun update( pojo: SourceFilePojo )

    @Delete
    fun delete( path: String )

    @Query("DELETE FROM Source_files" )
    fun deleteAll()

    @Query("SELECT * FROM Source_files" )
    fun selectAll(): List<SourceFilePojo>

    @Query("SELECT * FROM Source_files WHERE type = :type" )
    fun selectAllByType( type: SourceFilePojo.Type ): List<SourceFilePojo>

    @Query("SELECT * FROM Source_files WHERE type = :type AND path = :path LIMIT 1" )
    fun selectByPath( type: SourceFilePojo.Type, path: String ): SourceFilePojo
}