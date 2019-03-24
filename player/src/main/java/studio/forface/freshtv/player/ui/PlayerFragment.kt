package studio.forface.freshtv.player.ui

import androidx.fragment.app.Fragment
import studio.forface.freshtv.commonandroid.frameworkcomponents.RootFragment
import studio.forface.freshtv.player.R

/**
 * @author Davide Giuseppe Farella
 * A [Fragment] for the video player and relative info.
 *
 * Inherit from [RootFragment]
 */
class PlayerFragment: RootFragment( R.layout.fragment_player ) {

    companion object {
        /** A key for [channelId] argument */
        const val ARG_CHANNEL_ID = "channelId"

        /** @return `NavDirections` to this `Fragment` */
        //fun directions() = PlayerFragmentDirections.actionToTvChannelsFragment()
    }

    /** A [String] received from [getArguments] for retrieve the `Channel` with the given `id` */
    internal val channelId by lazy { requireArguments().getString( ARG_CHANNEL_ID ) }
}