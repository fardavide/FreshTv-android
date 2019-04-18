package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.ChannelIdAndPosition
import studio.forface.freshtv.domain.entities.id
import studio.forface.freshtv.domain.entities.position
import studio.forface.freshtv.domain.gateways.AppSettings

/**
 * Save id and position for the last selected `MovieChannel` shown in first position of the screen
 *
 * @author Davide Giuseppe Farella
 */
class SaveLastMovieChannelIdAndPosition(private val appSettings: AppSettings ) {

    /** Save the id and position for the last selected `MovieChannel` shown in first position of the screen */
    operator fun invoke( channelId: String?, position: Int? ) {
        appSettings.lastMovieChannelId = channelId
        appSettings.lastMovieChannelPosition = position
    }

    /** Save the id and position for the last selected `MovieChannel` shown in first position of the screen */
    operator fun invoke( channelIdAndPosition: ChannelIdAndPosition? ) {
        appSettings.lastMovieChannelId = channelIdAndPosition?.id
        appSettings.lastMovieChannelPosition = channelIdAndPosition?.position
    }
}