package studio.forface.freshtv.player.uiModels

import studio.forface.freshtv.player.uiModels.ChannelInfoUiModel.Tv.Program

/**
 * A sealed class representing additional info about the current `Channel`
 *
 * @author Davide Giuseppe Farella
 */
sealed class ChannelInfoUiModel {

    /** A data class containing info of the current `MovieChannel` TODO */
    object Movie : ChannelInfoUiModel()

    /**
     * A data class containing a [List] of [Program] of the current `TvChannel`
     *
     * @param currentIndex an [Int] of the index of the current [Program] ( by its [Program.startTime] and
     * [Program.endTime] )
     */
    data class Tv( val programs: List<Program>, val currentIndex: Int ) : ChannelInfoUiModel() {

        /** A data class containing info of a Program of the current `TvChannel` */
        data class Program(
            val title: String,
            val description: String,
            val image: String?,
            val category: String?,
            val year: String?,
            val country: String?,
            val director: String?,
            val actors: List<String>,
            val rating: String?,
            val startTime: String?,
            val endTime: String
        )
    }

}