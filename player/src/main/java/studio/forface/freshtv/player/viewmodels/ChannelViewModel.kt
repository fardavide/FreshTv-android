package studio.forface.freshtv.player.viewmodels

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.commonandroid.interactors.ChannelChangeFavoriteInteractor
import studio.forface.freshtv.player.presenters.ChannelPresenter
import studio.forface.viewstatestore.LockedViewStateStore
import studio.forface.viewstatestore.ViewStateStore

/**
 * A [ViewModel] that get `TvChannel`s
 * Inherit from [ScopedViewModel]
 *
 * @author Davide Giuseppe Farella
 */
internal class ChannelViewModel(
    private val channelId: String,
    private val presenter: ChannelPresenter,
    private val interactor: ChannelChangeFavoriteInteractor
) : ScopedViewModel( IO ) {

    /** A [Boolean] for keep track of the current favorite state of the `Channel` */
    private var favorite = false

    /** A [LockedViewStateStore] of [DrawableRes] for the favorite icon to show */
    val favoriteIcon = ViewStateStore<Int>().lock

    init {
        launch {
            for ( channel in presenter( channelId ) ) {
                favorite = channel.favorite
                favoriteIcon.postData( channel.favoriteImage )
            }
        }
    }

    /** Change the Favorite state of the current `Channel` */
    fun toggleFavorite() {
        interactor( channelId to ! favorite )
    }
}