package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.gateways.AppSettings

/**
 * Save name for the last selected `TvChannel`s `Group`
 *
 * @author Davide Giuseppe Farella
 */
class SaveLastTvChannelGroupName( private val appSettings: AppSettings ) {

    /** Save OPTIONAL [String] representing the name of the last selected `TvChannel`s `Group` */
    operator fun invoke( groupName: String? ) {
        appSettings.lastTvChannelGroupName = groupName
    }
}