package studio.forface.freshtv.commonandroid.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.navigation.NavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import studio.forface.freshtv.commonandroid.R
import studio.forface.freshtv.commonandroid.utils.Android
import studio.forface.freshtv.commonandroid.utils.getThemeColor
import studio.forface.freshtv.commonandroid.utils.postDelayed
import studio.forface.freshtv.domain.utils.optWith
import studio.forface.materialbottombar.doOnPanelClose
import studio.forface.materialbottombar.doOnPanelState
import studio.forface.materialbottombar.dsl.MaterialPanel
import studio.forface.materialbottombar.layout.MaterialBottomDrawerLayout
import studio.forface.materialbottombar.layout.PanelChangeListener
import studio.forface.materialbottombar.set

/**
 * A base class for `Activity` with navigation
 *
 * @param layoutRes The [LayoutRes] of the Layout for our `Activity`
 *
 * Inherit from [BaseActivity]
 *
 * @author Davide Giuseppe Farella
 */
abstract class NavActivity( @LayoutRes layoutRes: Int ): BaseActivity( layoutRes ) {

    /** @return the `Activity`s [AppBarLayout] */
    protected abstract val appBar: AppBarLayout

    /** @return the `Activity`s [MaterialBottomDrawerLayout] */
    protected abstract val drawerLayout: MaterialBottomDrawerLayout

    /** @return the `Activity`s [FloatingActionButton] */
    abstract val fab: FloatingActionButton

    /** `true` if the current [ParentFragment.isRootFragment] */
    private var isAtRoot = true

    /** Keep track whether the drawer is open */
    private var isDrawerOpen = false

    /** @return the `Activity`s [NavController] */
    protected abstract val navController: NavController

    /** @return the `Activity`s [TextView] for the title */
    protected abstract val titleTextView: TextView

    /** When the `Activity` is Created */
    override fun onCreate( savedInstanceState: Bundle? ) {
        window.decorView.systemUiVisibility = when {
            Android.OREO -> View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            Android.MARSHMALLOW -> View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            else -> window.decorView.systemUiVisibility
        }

        super.onCreate( savedInstanceState )

        drawerLayout.doOnPanelState { _, state ->
            isDrawerOpen = state != MaterialBottomDrawerLayout.Fly.BOTTOM
        }
    }

    /** Called when a [BaseFragment] is resumed */
    override fun onFragmentResumed( fragment: BaseFragment) {
        if ( fragment is ParentFragment) {
            isAtRoot = fragment.isRootFragment

            // If fabParams is null, hide and reset fab. We call it now for avoid glitches
            if ( fragment.fabParams == null ) {
                fab.hide()
                fab.setImageBitmap( null )
                fab.setOnClickListener( null )
            }

            // Bars
            fun setBars() {
                postDelayed(300 ) { toggleBars( fragment.hasBars ) }
            }

            // Title
            fun setTitle() {
                fragment.title?.let { titleTextView.text = it }
                titleTextView.setTextColor( fragment.titleColor )
            }

            // Navigation Icon
            @Suppress("RedundantLambdaArrow") // Needed for Click Listener
            fun setNavigationIcon() {
                val ( icon, action ) = if ( isAtRoot ) {
                    R.drawable.ic_hamburger to { _: View -> drawerLayout.openDrawer() }
                } else {
                    R.drawable.ic_left_arrow_black to { _: View -> onBackPressed() }
                }
                drawerLayout.bottomAppBar?.apply {
                    setNavigationIcon( icon )
                    setNavigationOnClickListener( action )
                }
            }

            // Options Menu
            fun setOptionsMenu() = fragment.setHasOptionsMenu( fragment.menuRes != null )

            // Background
            fun setBg() =
                setBackgroundColor( fragment.backgroundColor ?: getThemeColor( android.R.attr.colorBackground ) )

            // Fab
            fun setFab() {
                optWith( fragment.fabParams ) {
                    fab.setImageResource( drawableRes )
                    // fab.setText( textRes ) // TODO Fab can't have a Text, but an extended Fab would be great
                    fab.setOnClickListener( action )
                }
            }
            fun showOrHideFab() =
                if ( fragment.fabParams?.showOnStart == true ) fab.show() else fab.hide()

            // Setup
            val setup: PanelChangeListener = {
                setBars()
                setTitle()
                setNavigationIcon()
                setOptionsMenu()
                setBg()
                setFab()
                showOrHideFab()
            }

            // If isDrawerOpen run setup after close, else run it now
            if ( isDrawerOpen ) drawerLayout.doOnPanelClose( once = true, callback = setup )
            else setup(0 )
        }
    }

    /**
     * When *back* is pressed, close the Drawer if is open, else close the Activity if [isAtRoot] else call
     * [onBackPressed]
     */
    override fun onBackPressed() {
        if ( ! ( closeDrawer() || closeIfAtRoot() )  ) super.onBackPressed()
    }

    /** Call [NavController.navigateUp] and if [navController] can't navigate up, call [back] */
    override fun onNavigateUp() = navController.navigateUp() || back()

    /** Call [onBackPressed] and return `false` */
    private fun back(): Boolean {
        super.onBackPressed()
        return false
    }

    /**
     * Call [finish] if [isAtRoot]
     * @return `true` if [finish] has been called
     */
    private fun closeIfAtRoot(): Boolean {
        if ( isAtRoot ) finish()
        return isAtRoot
    }

    /**
     * Close [drawerLayout] if open
     * @return `true` if [drawerLayout] has been close, false if it was already closed
     */
    private fun closeDrawer(): Boolean {
        val wasDrawerOpen = isDrawerOpen
        if ( wasDrawerOpen ) drawerLayout.closeDrawer()
        return wasDrawerOpen
    }

    /** Close a [MaterialPanel] and remove it from [drawerLayout] */
    fun dismissAndRemovePanel( panelId: Int ) {
        drawerLayout.closePanel()
        drawerLayout.removePanel( panelId )
    }

    /** Close a [MaterialPanel] */
    fun dismissPanel() {
        drawerLayout.closePanel()
    }

    /**
     * Set a background color to `Activity`.
     * @param color the [ColorInt] color to set as background.
     */
    private fun setBackgroundColor( @ColorInt color: Int ) {
        rootView.setBackgroundColor( color )
    }

    /**
     * Set a background color resource to `Activity`.
     * @param colorId the [ColorRes] resource of the color to set as background.
     */
    fun setBackgroundColorRes( @ColorRes colorId: Int ) {
        rootView.setBackgroundResource( colorId )
    }

    /** Add the given [MaterialPanel] to [drawerLayout] and open it. */
    fun showPanel( panelId: Int, panel: MaterialPanel ) {
        drawerLayout[panelId] = panel
        drawerLayout.openPanel( panelId )
    }

    /** Toggle the visibility of the bars */
    private fun toggleBars( show: Boolean ) {
        if ( show ) {
            drawerLayout.showBars()
            appBar.setExpanded( true )
        } else {
            drawerLayout.hideBars()
            appBar.setExpanded( false )
        }
    }
}

/**
 * @return OPTIONAL [NavActivity] from a [NavFragment] ( optional since the [NavFragment.getActivity] is also
 * nullable ).
 */
internal val NavFragment.navActivity get() = activity as? NavActivity