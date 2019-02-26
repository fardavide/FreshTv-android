package studio.forface.freshtv.ui

import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.frameworkcomponents.RootFragment

/**
 * @author Davide Giuseppe Farella
 * Todo
 */
class PlaylistsFragment: RootFragment( R.layout.fragment_recycler_view ) {

    /** @see RootFragment.titleRes */
    override val titleRes get() = R.string.menu_my_playlists
}