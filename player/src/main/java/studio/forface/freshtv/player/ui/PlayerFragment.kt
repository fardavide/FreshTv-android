package studio.forface.freshtv.player.ui

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.viewModel
import org.koin.core.parameter.parametersOf
import studio.forface.freshtv.commonandroid.frameworkcomponents.NestedFragment
import studio.forface.freshtv.commonandroid.frameworkcomponents.RootFragment
import studio.forface.freshtv.player.R
import studio.forface.freshtv.player.viewmodels.ChannelSourceViewModel

/**
 * @author Davide Giuseppe Farella
 * A [Fragment] for the video player and relative info.
 *
 * Inherit from [RootFragment]
 */
class PlayerFragment: RootFragment( R.layout.fragment_player ) {

    companion object {
        /** A key for [channelId] argument */
        const val ARG_CHANNEL_ID = "extra.channel-id"

        /** @return `NavDirections` to this `Fragment` */
        //fun directions() = PlayerFragmentDirections.actionToTvChannelsFragment()
    }

    /** A [String] received from [getArguments] for retrieve the `Channel` with the given `id` */
    private val channelId by lazy { arguments?.getString( ARG_CHANNEL_ID ) }

    /** A [NestedFragment] for the player */
    class VideoFragment( _channelId: String ): NestedFragment( R.layout.fragment_player_video ) {

        /** @return [String] [ARG_CHANNEL_ID] from [getArguments] */
        private val channelId by lazy { requireArguments().getString( ARG_CHANNEL_ID ) }

        init {
            arguments = ( arguments ?: bundleOf() ).apply {
                putString( ARG_CHANNEL_ID, _channelId )
            }
        }

        /** A reference to [ChannelSourceViewModel] */
        private val sourceViewModel by viewModel<ChannelSourceViewModel> { parametersOf( channelId ) }
    }

    /** A [NestedFragment] for the info */
    class InfoFragment( _channelId: String ): NestedFragment( R.layout.fragment_player_info ) {

        /** @return [String] [ARG_CHANNEL_ID] from [getArguments] */
        private val channelId by lazy { requireArguments().getString( ARG_CHANNEL_ID ) }

        init {
            arguments = ( arguments ?: bundleOf() ).apply {
                putString( ARG_CHANNEL_ID, _channelId )
            }
        }
    }
}