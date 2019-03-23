package studio.forface.freshtv.player.ui

import studio.forface.freshtv.commonandroid.frameworkcomponents.NestedFragment
import studio.forface.freshtv.player.R

/**
 * A [NestedFragment] for the info for [PlayerFragment]
 *
 * @author Davide Giuseppe Farella
 */
internal class InfoFragment : NestedFragment<PlayerFragment>( R.layout.fragment_player_info ) {

    /** @return [String] Channel id from [rootFragment] */
    private val channelId by lazy { rootFragment.channelId }
}