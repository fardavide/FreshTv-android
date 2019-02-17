package studio.forface.freshtv.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.viewModel
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.frameworkcomponents.BaseActivity
import studio.forface.freshtv.commonandroid.frameworkcomponents.BaseFragment
import studio.forface.freshtv.domain.utils.handle
import studio.forface.freshtv.ui.HomeFragmentDirections.Companion.actionToEditPlaylistFragment
import studio.forface.freshtv.ui.HomeFragmentDirections.Companion.actionToEpgsFragment
import studio.forface.freshtv.uimodels.*
import studio.forface.freshtv.viewmodels.ChannelsAvailabilityViewModel
import studio.forface.materialbottombar.layout.MaterialBottomDrawerLayout
import studio.forface.materialbottombar.navigation.dsl.MaterialNavDrawer
import studio.forface.materialbottombar.navigation.dsl.navDrawer
import studio.forface.materialbottombar.panels.params.*
import kotlinx.android.synthetic.main.activity_main.fab as syntheticFab
import kotlinx.android.synthetic.main.activity_main.titleTextView as syntheticTitleTextView

/**
 * @author Davide Giuseppe Farella
 * The Main `Activity` of the app
 *
 * Inherit from [BaseActivity]
 */
internal class MainActivity: BaseActivity( R.layout.activity_main ) {

    /** @see BaseActivity.drawerLayout */
    override val drawerLayout: MaterialBottomDrawerLayout get() = root

    /** A `ViewModel` for check the availability of Channels */
    private val channelsAvailabilityViewModel by viewModel<ChannelsAvailabilityViewModel>()

    /** @see BaseActivity.fab */
    override val fab: FloatingActionButton get() = syntheticFab

    /** @see BaseActivity.navController */
    override val navController: NavController get() = findNavController( R.id.nav_host )

    /** @see BaseActivity.rootView */
    override val rootView: CoordinatorLayout get() = root

    /** @see BaseActivity.titleTextView */
    override val titleTextView: TextView get() = syntheticTitleTextView

    /** When the `Activity` is Created */
    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setDrawer()
        setSupportActionBar( bottomAppBar )
        // bottomAppBar.setupWithNavController( navController ) TODO: navigationIcon not showing

        channelsAvailabilityViewModel.channelsAvailability.observe {
            doOnError { notifier.error( it ) }
            doOnData( ::onChannelsAvailability )
        }
    }

    /**
     * @return a [MaterialNavDrawer], build with the given [ChannelsAvailabilityUiModel] for our
     * [MaterialBottomDrawerLayout]
     *
     * If [ChannelsAvailabilityUiModel.hasMovies] is true the corresponding item will be shown.
     * If [ChannelsAvailabilityUiModel.hasTvs] is true the corresponding item will be shown.
     * If both of them are false, or [channelsAvailability] is null, an item for add a `Playlist` will be shown
     */
    private fun buildDrawer( channelsAvailability: ChannelsAvailabilityUiModel? ) = navDrawer( navController ) {
        header {
            titleTextRes = R.string.app_name
            titleBold = true
        }
        body {
            allPrimary {
                titleBold = false
                titleSpSize = 14f
                iconDpSize = 36f
            }
            
            // Add playlist
            if ( channelsAvailability.hasNothing )
                primaryItem( R.string.action_add_playlist ) {
                    iconResource = R.drawable.ic_playlist
                    navDirections = actionToEditPlaylistFragment(null )
                }

            // Tv Channels
            if ( channelsAvailability.hasTvs )
                primaryItem( R.string.menu_tv_channels ) {
                    iconResource = R.drawable.ic_tv
                }

            // Movie Channels
            if ( channelsAvailability.hasMovies )
                primaryItem( R.string.menu_movie_channels ) {
                    iconResource = R.drawable.ic_movie
                }

            // Divider
            divider()

            // My Playlists
            if ( channelsAvailability.hasAny )
                primaryItem( R.string.menu_my_playlists ) {
                    iconResource = R.drawable.ic_playlist
                }

            // My EPGs
            primaryItem( R.string.menu_my_epgs ) {
                iconResource = R.drawable.ic_epg
                navDirections = actionToEpgsFragment()
            }

            // Divider
            divider()

            // Settings
            primaryItem( R.string.menu_settings ) {
                iconResource = R.drawable.ic_settings
            }
        }
    }

    /** When [ChannelsAvailabilityUiModel] is received from [ChannelsAvailabilityViewModel] */
    private fun onChannelsAvailability( channelsAvailability: ChannelsAvailabilityUiModel ) {
        setDrawer( channelsAvailability )
        // TODO navigate
    }

    /** [buildDrawer] and set as [MaterialBottomDrawerLayout.drawer] of [root] [View] */
    private fun setDrawer( channelsAvailability: ChannelsAvailabilityUiModel? = null ) {
        runCatching { root.drawer = buildDrawer( channelsAvailability ) }
                .onFailure { notifier.error( it ) }
    }
}

/**
 * @return OPTIONAL [MainActivity] from a [BaseFragment] ( optional since the
 * [BaseFragment.getActivity] is also nullable ).
 */
internal val BaseFragment.mainActivity get() = activity as? MainActivity