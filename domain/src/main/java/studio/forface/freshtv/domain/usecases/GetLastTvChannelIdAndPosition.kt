package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.ChannelIdAndPosition
import studio.forface.freshtv.domain.gateways.AppSettings

/**
 * Get id and position for the last selected `TvChannel` shown in first position of the screen
 *
 * @author Davide Giuseppe Farella
 */
class GetLastTvChannelIdAndPosition( private val appSettings: AppSettings ) {

    /**
     * @return OPTIONAL [ChannelIdAndPosition] representing the id and the position of the last selected `TvChannel`
     * shown at first position of the screen
     */
    operator fun invoke(): ChannelIdAndPosition? {
        val id = appSettings.lastTvChannelId ?: return null
        val position = appSettings.lastTvChannelPosition ?: return null
        return id to position
    }
}