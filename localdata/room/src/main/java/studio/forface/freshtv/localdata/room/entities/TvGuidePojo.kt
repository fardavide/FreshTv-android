package studio.forface.freshtv.localdata.room.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.domain.entities.Url

@Entity( tableName = "Tv_guide" )
data class TvGuidePojo(

    @PrimaryKey
    val id: String,

    val channelId: String,

    val title: String,

    val description: String,

    val imageUrl: Url?,

    val category: String?,

    val year: Int?,

    val country: String?,

    @Embedded( prefix = "credits_" )
    val credits: Credits?,

    val rating: String?,

    val startTime: LocalDateTime,

    val endTime: LocalDateTime
) {
    data class Credits( val director: String?, val actors: List<String> )
}