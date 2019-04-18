package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.gateways.AppSettings

/**
 * Get name for the last selected `MovieChannel`s `Group`
 *
 * @author Davide Giuseppe Farella
 */
class GetLastMovieChannelGroupName( private val appSettings: AppSettings ) {

    /** @return OPTIONAL [String] representing the name of the last selected `MovieChannel`s `Group` */
    operator fun invoke() = appSettings.lastMovieChannelGroupName
}