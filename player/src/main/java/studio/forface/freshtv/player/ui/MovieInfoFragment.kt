package studio.forface.freshtv.player.ui

import studio.forface.freshtv.commonandroid.frameworkcomponents.NestedFragment
import studio.forface.freshtv.player.R

/**
 * A [NestedFragment] for the info of Movie for [PlayerFragment]
 *
 * @author Davide Giuseppe Farella
 */
internal class MovieInfoFragment : NestedFragment<PlayerFragment>( R.layout.fragment_movie_info ) {

    /** @return [String] Channel id from [InfoFragment] */
    private val channelId by lazy { parentBaseFragment.channelId }

    // TODO
}