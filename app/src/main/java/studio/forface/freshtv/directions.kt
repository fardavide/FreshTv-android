package studio.forface.freshtv

import studio.forface.freshtv.domain.utils.Invokable
import studio.forface.freshtv.ui.MovieChannelGroupsFragmentDirections
import studio.forface.freshtv.ui.MovieChannelsFragment
import studio.forface.freshtv.ui.TvChannelGroupsFragmentDirections
import studio.forface.freshtv.ui.TvChannelsFragment

/** Shadow of [studio.forface.freshtv.player.ui.PlayerFragment] */
@Suppress("unused")
object PlayerFragment : Invokable {

    /** @return `NavDirections` to `PlayerFragment` `Fragment` from [TvChannelsFragment] */
    internal fun TvChannelsFragment.directions( channelId: String ) =
        TvChannelGroupsFragmentDirections.actionTvChannelsFragmentToPlayerFragment( channelId )

    /** @return `NavDirections` to `PlayerFragment` `Fragment` from [MovieChannelsFragment] */
    internal fun MovieChannelsFragment.directions(channelId: String ) =
        MovieChannelGroupsFragmentDirections.actionMovieChannelsFragmentToPlayerFragment( channelId )
}

/** Shadow of [studio.forface.freshtv.preferences.ui.PreferencesFragment] */
object PreferencesFragment : Invokable {
    /** @return `NavDirections` to `PreferencesFragment` `Fragment` */
    fun directions() = NavGraphDirections.actionToPreferencesFragment()
}