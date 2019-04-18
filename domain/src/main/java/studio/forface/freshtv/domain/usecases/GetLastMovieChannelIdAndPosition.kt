package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.ChannelIdAndPosition
import studio.forface.freshtv.domain.gateways.AppSettings

/**
 * Get id and position for the last selected `MovieChannel` shown in first position of the screen
 *
 * @author Davide Giuseppe Farella
 */
class GetLastMovieChannelIdAndPosition( private val appSettings: AppSettings ) {

    /**
     * @return OPTIONAL [ChannelIdAndPosition] representing the id and the position of the last selected `MovieChannel`
     * shown at first position of the screen
     */
    operator fun invoke(): ChannelIdAndPosition? {
        val id = appSettings.lastMovieChannelId ?: return null
        val position = appSettings.lastMovieChannelPosition ?: return null
        return id to position
    }
}