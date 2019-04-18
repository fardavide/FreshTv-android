package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.gateways.AppSettings

/**
 * Get name for the last selected `TvChannel`s `Group`
 *
 * @author Davide Giuseppe Farella
 */
class GetLastTvChannelGroupName( private val appSettings: AppSettings ) {

    /** @return OPTIONAL [String] representing the name of the last selected `TvChannel`s `Group` */
    operator fun invoke() = appSettings.lastTvChannelGroupName
}