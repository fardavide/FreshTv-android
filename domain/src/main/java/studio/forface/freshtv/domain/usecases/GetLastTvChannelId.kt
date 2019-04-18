package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.gateways.AppSettings

/**
 * Get id for the last selected `TvChannel` shown in the center of the screen
 *
 * @author Davide Giuseppe Farella
 */
class GetLastTvChannelId( private val appSettings: AppSettings ) {

    /**
     * @return OPTIONAL [String] representing the id of the last selected `TvChannel` shown in the center of the
     * screen
     */
    operator fun invoke() = appSettings.lastTvChannelId
}