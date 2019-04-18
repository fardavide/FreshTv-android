package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.gateways.AppSettings

/**
 * Save name for the last selected `MovieChannel`s `Group`
 *
 * @author Davide Giuseppe Farella
 */
class SaveLastMovieChannelGroupName( private val appSettings: AppSettings ) {

    /** Save OPTIONAL [String] representing the name of the last selected `MovieChannel`s `Group` */
    operator fun invoke( groupName: String? ) {
        appSettings.lastMovieChannelGroupName = groupName
    }
}