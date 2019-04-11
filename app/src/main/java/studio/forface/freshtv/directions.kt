package studio.forface.freshtv

import studio.forface.freshtv.domain.utils.Invokable
import studio.forface.freshtv.ui.MovieChannelGroupsFragmentDirections
import studio.forface.freshtv.ui.MovieChannelsFragment
import studio.forface.freshtv.ui.TvChannelGroupsFragmentDirections
import studio.forface.freshtv.ui.TvChannelsFragment

/** Shadow of [studio.forface.freshtv.player.ui.PlayerFragment] */
@Suppress("unused")
object PlayerFragment : Invokable {

    /** @return `NavDirections` to `PlayerActivity` from [TvChannelsFragment] */
    internal fun TvChannelsFragment.directions( channelId: String ) =
        TvChannelGroupsFragmentDirections.actionTvChannelsFragmentToPlayerActivity( channelId )

    /** @return `NavDirections` to `PlayerActivity` from [MovieChannelsFragment] */
    internal fun MovieChannelsFragment.directions(channelId: String ) =
        MovieChannelGroupsFragmentDirections.actionMovieChannelsFragmentToPlayerActivity( channelId )
}

/** Shadow of [studio.forface.freshtv.about.ui.AboutFragment] */
object AboutFragment : Invokable {
    /** @return `NavDirections` to `PreferencesFragment` `Fragment` */
    fun directions() = NavGraphDirections.actionToAboutFragment()
}

/** Shadow of [studio.forface.freshtv.preferences.ui.PreferencesFragment] */
object PreferencesFragment : Invokable {
    /** @return `NavDirections` to `PreferencesFragment` `Fragment` */
    fun directions() = NavGraphDirections.actionToPreferencesFragment()
}