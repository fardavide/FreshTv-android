package studio.forface.freshtv.player.uiModels

import studio.forface.freshtv.domain.entities.TvGuide
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
     * @param currentIndex an [Int] of the index of the current [Program] ( by its [TvGuide.startTime] and
     * [TvGuide.endTime] )
     */
    data class Tv( val programs: List<Program>, val currentIndex: Int ) : ChannelInfoUiModel() {

        /** A data class containing minimal info of a Program of the current `TvChannel` */
        data class Program(
            val headerName: String,
            val tvGuideId: String
        )
    }
}