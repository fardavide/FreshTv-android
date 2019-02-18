package studio.forface.freshtv.localdata.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import studio.forface.freshtv.domain.entities.SourceFile

@Entity( tableName = "Source_files" )
data class SourceFilePojo(

    @PrimaryKey
    val path: String,

    val type: Type,

    val name: String?,

    val sourceType: SourceFile.Type
) {
    enum class Type { EPG, PLAYLIST }
}