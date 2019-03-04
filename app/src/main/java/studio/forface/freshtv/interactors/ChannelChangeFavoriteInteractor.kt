package studio.forface.freshtv.interactors

import studio.forface.freshtv.domain.usecases.FavoritedChannel
import studio.forface.freshtv.domain.usecases.UpdateChannelFavoriteState

/**
 * @author Davide Giuseppe Farella
 * An Interactor for Change the Favorite state of a `Channel`
 */
internal class ChannelChangeFavoriteInteractor(
        private val updateChannelFavoriteState: UpdateChannelFavoriteState
) {
    /** Change the Favorite state of a `Channel` */
    operator fun invoke( favoritedChannel: FavoritedChannel ) {
        updateChannelFavoriteState( favoritedChannel )
    }
}