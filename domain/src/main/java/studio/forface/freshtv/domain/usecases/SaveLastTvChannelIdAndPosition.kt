package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.ChannelIdAndPosition
import studio.forface.freshtv.domain.entities.id
import studio.forface.freshtv.domain.entities.position
import studio.forface.freshtv.domain.gateways.AppSettings

/**
 * Save id and position for the last selected `TvChannel` shown in first position of the screen
 *
 * @author Davide Giuseppe Farella
 */
class SaveLastTvChannelIdAndPosition(private val appSettings: AppSettings ) {

    /** Save the id and position for the last selected `TvChannel` shown in first position of the screen */
    operator fun invoke( channelId: String?, position: Int? ) {
        appSettings.lastTvChannelId = channelId
        appSettings.lastTvChannelPosition = position
    }

    /** Save the id and position for the last selected `TvChannel` shown in first position of the screen */
    operator fun invoke( channelIdAndPosition: ChannelIdAndPosition? ) {
        appSettings.lastTvChannelId = channelIdAndPosition?.id
        appSettings.lastTvChannelPosition = channelIdAndPosition?.position
    }
}