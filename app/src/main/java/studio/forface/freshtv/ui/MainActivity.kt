package studio.forface.freshtv.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.postDelayed
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.viewModel
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.frameworkcomponents.BaseActivity
import studio.forface.freshtv.commonandroid.frameworkcomponents.BaseFragment
import studio.forface.freshtv.uimodels.*
import studio.forface.freshtv.viewmodels.ChannelsAvailabilityViewModel
import studio.forface.materialbottombar.drawer.MaterialDrawer
import studio.forface.materialbottombar.layout.MaterialBottomDrawerLayout
import studio.forface.materialbottombar.navigation.dsl.navDrawer
import studio.forface.materialbottombar.panels.params.*
import kotlinx.android.synthetic.main.activity_main.toolbar as syntheticToolbar

/**
 * @author Davide Giuseppe Farella
 * The Main `Activity` of the app
 *
 * Inherit from [BaseActivity]
 */
class MainActivity: BaseActivity( R.layout.activity_main ) {

    /** A `ViewModel` for check the availability of Channels */
    private val channelsAvailabilityViewModel by viewModel<ChannelsAvailabilityViewModel>()

    /** @see BaseActivity.navController */
    override val navController: NavController get() = findNavController( R.id.nav_host )

    /** @see BaseActivity.rootView */
    override val rootView: View get() = root

    /** @see BaseActivity.toolbar */
    override val toolbar: Toolbar get() = syntheticToolbar

    /** When the `Activity` is Created */
    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setDrawer()
        setSupportActionBar( bottomAppBar )
        // bottomAppBar.setupWithNavController( navController ) TODO: navigationIcon not showing

        channelsAvailabilityViewModel.channelsAvailability
            .doOnError { notifier.error( it ) }
            .doOnData( ::setDrawer )
    }

    /**
     * @return a [MaterialDrawer], build with the given [ChannelsAvailabilityUiModel] for our
     * [MaterialBottomDrawerLayout]
     *
     * If [ChannelsAvailabilityUiModel.hasMovies] is true the corresponding item will be shown.
     * If [ChannelsAvailabilityUiModel.hasTvs] is true the corresponding item will be shown.
     * If both of them are false, or [channelsAvailability] is null, an item for add a `Playlist` will be shown
     */
    private fun buildDrawer( channelsAvailability: ChannelsAvailabilityUiModel? ) = navDrawer {
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
            }

            // Divider
            divider()

            // Settings
            primaryItem( R.string.menu_settings ) {
                iconResource = R.drawable.ic_settings
            }
        }
    }

    /** [buildDrawer] and set as [MaterialBottomDrawerLayout.drawer] of [root] [View] */
    private fun setDrawer( channelsAvailability: ChannelsAvailabilityUiModel? = null ) {
        runCatching { root.drawer = buildDrawer( channelsAvailability ) }
            .onSuccess { root.postDelayed(100 ) { root.openDrawer() } }
            .onFailure { notifier.error( it ) }
    }
}

/**
 * @return OPTIONAL [MainActivity] from a [BaseFragment] ( optional since the [BaseFragment.getActivity] is also
 * nullable ).
 */
val BaseFragment.mainActivity get() = activity as? MainActivity