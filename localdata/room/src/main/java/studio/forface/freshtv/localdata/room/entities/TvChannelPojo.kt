package studio.forface.freshtv.localdata.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import studio.forface.freshtv.domain.entities.Url

@Entity( tableName = "Tv_channel" )
data class TvChannelPojo (

    @PrimaryKey
    val id: String,

    val name: String,

    val groupName: String,

    val imageUrl: Url?,

    val mediaUrls: Map<String, Int>,

    val playlistPaths: List<String>,

    val favorite: Boolean
)