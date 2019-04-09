package studio.forface.freshtv.player.ui

import androidx.coordinatorlayout.widget.CoordinatorLayout
import kotlinx.android.synthetic.main.activity_player.*
import studio.forface.freshtv.commonandroid.ui.*
import studio.forface.freshtv.player.R

/**
 * An `Activity` for the video player and relative info
 *
 * Inherit from [BaseActivity]
 *
 * Implements [FullscreenEnabledComponent]
 * Implements [RotationEnabledComponent]
 *
 * @author Davide Giuseppe Farella
 */
class PlayerActivity: BaseActivity( R.layout.activity_player ),
    FullscreenEnabledComponent by FullscreenEnabledDelegate(),
    RotationEnabledComponent by RotationEnabledDelegate() {

    companion object {
        /** A key for [channelId] argument */
        const val ARG_CHANNEL_ID = "channelId"
    }

    /** A [String] received from [getIntent] for retrieve the `Channel` with the given `id` */
    internal val channelId by lazy { intent.extras!!.getString( ARG_CHANNEL_ID )!! }

    /** @see BaseActivity.rootView */
    override val rootView: CoordinatorLayout get() = root

    /** When [PlayerActivity] is started */
    override fun onStart() {
        initFullscreenEnabledDelegate(this )
        initRotationEnabledDelegate(this )
        super.onStart()

        if ( isLandscape() ) enterFullscreen()
        else exitFullscreen()
    }
}

/**
 * @return OPTIONAL [PlayerActivity] from a [PlayerFragment] ( optional since the [PlayerFragment.getActivity] is also
 * nullable ).
 */
internal val PlayerFragment.playerActivity get() = activity as? PlayerActivity