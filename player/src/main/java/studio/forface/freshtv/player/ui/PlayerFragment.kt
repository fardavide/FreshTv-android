package studio.forface.freshtv.player.ui

import androidx.fragment.app.Fragment
import studio.forface.freshtv.commonandroid.frameworkcomponents.NestedFragment
import studio.forface.freshtv.commonandroid.frameworkcomponents.RootFragment
import studio.forface.freshtv.player.R

/**
 * @author Davide Giuseppe Farella
 * A [Fragment] for the video player and relative info.
 *
 * Inherit from [RootFragment]
 */
class PlayerFragment: RootFragment(R.layout.fragment_player) {

    /** A [NestedFragment] for the player */
    class VideoFragment: NestedFragment(R.layout.fragment_player_video)

    /** A [NestedFragment] for the info */
    class InfoFragment: NestedFragment(R.layout.fragment_player_info)

}