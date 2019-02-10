package studio.forface.freshtv.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.frameworkcomponents.BaseActivity
import studio.forface.freshtv.commonandroid.frameworkcomponents.BaseFragment
import studio.forface.materialbottombar.drawer.MaterialDrawer
import studio.forface.materialbottombar.dsl.drawer
import studio.forface.materialbottombar.layout.MaterialBottomDrawerLayout
import studio.forface.materialbottombar.panels.params.titleBold
import studio.forface.materialbottombar.panels.params.titleTextRes
import kotlinx.android.synthetic.main.activity_main.toolbar as syntheticToolbar

/**
 * @author Davide Giuseppe Farella
 * The Main `Activity` of the app
 *
 * Inherit from [BaseActivity]
 */
class MainActivity: BaseActivity( R.layout.activity_main ) {

    /**
     * @return a [MaterialDrawer] for our [MaterialBottomDrawerLayout]
     *
     * @param hasChannels represents whether any `Channel` is present in database, so che corresponding item will be shown
     * @param hasMovies represents whether any `Movie` is present in database, so che corresponding item will be shown
     * If both [hasChannels] and [hasMovies] are false, an item for add a `Playlist` will be shown
     */
    private fun buildDrawer( hasChannels: Boolean = false, hasMovies: Boolean = false ) = drawer {
        header {
            titleTextRes = R.string.app_name
            titleBold = true
        }
        body {

        }
    }

    /** @see BaseActivity.navController */
    override val navController: NavController get() = findNavController( R.id.nav_host )

    /** @see BaseActivity.rootView */
    override val rootView: View get() = root

    /** @see BaseActivity.toolbar */
    override val toolbar: Toolbar get() = syntheticToolbar

    /** When the `Activity` is Created */
    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        root.drawer = buildDrawer()
        setSupportActionBar( bottomAppBar )
        bottomAppBar.setupWithNavController( navController )
    }

    // TODO remove
    fun test() {
        na
    }
}

/**
 * @return OPTIONAL [MainActivity] from a [BaseFragment] ( optional since the [BaseFragment.getActivity] is also
 * nullable ).
 */
val BaseFragment.mainActivity get() = activity as? MainActivity