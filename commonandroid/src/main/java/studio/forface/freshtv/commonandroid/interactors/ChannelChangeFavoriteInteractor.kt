package studio.forface.freshtv.commonandroid.interactors

import studio.forface.freshtv.domain.usecases.FavoritedChannel
import studio.forface.freshtv.domain.usecases.UpdateChannelFavoriteState

/**
 * An Interactor for Change the Favorite state of a `Channel`
 *
 * @author Davide Giuseppe Farella
 */
class ChannelChangeFavoriteInteractor(
        private val updateChannelFavoriteState: UpdateChannelFavoriteState
) {
    /** Change the Favorite state of a `Channel` */
    operator fun invoke( favoritedChannel: FavoritedChannel ) {
        updateChannelFavoriteState( favoritedChannel )
    }
}