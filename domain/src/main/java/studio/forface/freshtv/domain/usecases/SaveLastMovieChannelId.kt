package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.gateways.AppSettings

/**
 * Save id for the last selected `MovieChannel` shown in the center of the screen
 *
 * @author Davide Giuseppe Farella
 */
class SaveLastMovieChannelId( private val appSettings: AppSettings ) {

    /** Save the id of the last selected `MovieChannel` shown in the center of the screen */
    operator fun invoke( channelId: String? ) {
        appSettings.lastMovieChannelId = channelId
    }
}