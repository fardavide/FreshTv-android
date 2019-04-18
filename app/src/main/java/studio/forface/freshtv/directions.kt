package studio.forface.freshtv

import studio.forface.freshtv.domain.utils.Invokable
import studio.forface.freshtv.ui.*
import studio.forface.freshtv.ui.ChannelsFragment

/** Shadow of [studio.forface.freshtv.player.ui.PlayerFragment] */
@Suppress("unused")
object PlayerFragment : Invokable {

    /** @return `NavDirections` to `PlayerActivity` from [ChannelsFragment] */
    internal fun ChannelsFragment<*>.directions(channelId: String ) =
        TvChannelGroupsFragmentDirections.actionTvChannelsFragmentToPlayerActivity( channelId )
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