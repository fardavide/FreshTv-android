package studio.forface.freshtv.localdata.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import studio.forface.freshtv.domain.entities.ChannelGroup
import studio.forface.freshtv.domain.entities.Url

@Entity( tableName = "Channel_groups" )
data class ChannelGroupPojo(

    @PrimaryKey
    val id: String,

    val name: String,

    val type: ChannelGroup.Type, //String,

    val imageUrl: Url?

)