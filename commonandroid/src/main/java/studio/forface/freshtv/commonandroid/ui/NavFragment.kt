package studio.forface.freshtv.commonandroid.ui

import android.graphics.Color
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.annotation.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import studio.forface.freshtv.commonandroid.R
import studio.forface.freshtv.commonandroid.frameworkcomponents.AndroidUiComponent
import studio.forface.freshtv.commonandroid.utils.getColor
import studio.forface.freshtv.commonandroid.utils.getThemeColor
import studio.forface.freshtv.commonandroid.utils.inflate
import studio.forface.materialbottombar.dsl.MaterialPanel
import studio.forface.materialbottombar.dsl.PanelBuilder
import studio.forface.materialbottombar.dsl.panel
import studio.forface.materialbottombar.panels.params.titleColorRes
import studio.forface.materialbottombar.panels.params.titleSpSize
import studio.forface.materialbottombar.panels.params.titleTextRes
import studio.forface.theia.dsl.TheiaFragment

/**
 * A base class for [Fragment]'s with navigation
 * The class is sealed since we don't want other implementation that the ones declared here.
 *
 * @param layoutRes The [LayoutRes] of the Layout we will inflate in our [Fragment]
 *
 * Inherit from [TheiaFragment].
 * Implements [AndroidUiComponent]
 *
 * @author Davide Giuseppe Farella
 */
sealed class NavFragment( @LayoutRes layoutRes: Int ) : BaseFragment( layoutRes ) {

    /** The [Fragment]'s [NavController] */
    val navController: NavController by lazy { findNavController() }

    /** Close a [MaterialPanel] and remove it */
    fun dismissAndRemovePanel( panelId: Int ) {
        navActivity?.dismissAndRemovePanel( panelId )
    }

    /** Close a [MaterialPanel] */
    fun dismissPanel() {
        navActivity?.dismissPanel()
    }

    /** Shortcut for show a dialog [MaterialPanel] with a static id */
    fun showDialog(
        @StringRes titleRes: Int,
        @StringRes contentRes: Int? = null,
        @StringRes positiveTextRes: Int? = null,
        @StringRes negativeTextRes: Int? = null,
        onNegativeButton: () -> Unit = {},
        onPositiveButton: () -> Unit = {}
    ) {
        val body = inflate( R.layout.layout_dialog_body ).apply {

            // Set content
            contentRes?.let { findViewById<TextView>( R.id.dialogContentTextView ).setText( it ) }

            // Set positive Button
            findViewById<Button>( R.id.dialogPositiveButton ).apply {
                positiveTextRes?.let { setText( it ) }
                setOnClickListener { dismissPanel(); onPositiveButton() }
            }

            // Set negative Button
            findViewById<Button>( R.id.dialogNegativeButton ).apply {
                negativeTextRes?.let { setText( it ) }
                setOnClickListener { dismissPanel(); onNegativeButton() }
            }
        }
        val panel = panel {
            // Set Title
            header {
                titleTextRes = titleRes
                titleColorRes = R.color.colorPrimary
                titleSpSize = 16f
            }
            customBody( body )
        }
        showPanel(DIALOG_PANEL_ID, panel )
    }

    /** Add the given [MaterialPanel] and open it. */
    fun showPanel( panelId: Int, panel: MaterialPanel ) {
        navActivity?.showPanel( panelId, panel )
    }

    /** Add the [MaterialPanel] created through [builder] and open it. */
    fun showPanel( panelId: Int, builder: PanelBuilder.() -> Unit ) {
        showPanel( panelId, panel { builder() } )
    }
}

/** An [Int] ID for [NavFragment.showPanel] for a generic dialog [MaterialPanel] */
private const val DIALOG_PANEL_ID = 909

/**
 * A base class for a [Fragment] that have an `Activity` as parent
 * Inherit from [NavFragment]
 */
abstract class ParentFragment( @LayoutRes layoutRes: Int ): NavFragment( layoutRes ) {
    /**
     * This value will contain a [ColorInt] for the background of the app.
     * If this value is not overridden, we call [getColor] on [backgroundColorRes].
     * @see backgroundColorRes
     */
    open val backgroundColor: Int? get() = backgroundColorRes?.let { getColor( it ) }

    /**
     * This value will contains a [ColorRes] for the background of the app.
     * If this value is not overridden, we return null.
     * This value will be used from [backgroundColor].
     * DO NOT CALL IT DIRECTLY, SINCE [backgroundColor] CAN OVERRIDE IT!
     */
    @get:ColorRes open val backgroundColorRes: Int? get() = null

    /** @return OPTIONAL [NavActivity.fab] */
    protected val fab get() = navActivity?.fab

    /** The OPTIONAL [FabParams] for setup a `FloatingActionButton` in the `Activity` */
    open val fabParams: FabParams? = null

    /** A [Boolean] representing whether AppBars must be visible for this [Fragment] */
    open val hasBars = true

    /**
     * A [Boolean] representing whether this [ParentFragment] is a root Fragment
     * If `true`, when navigating back, the [NavActivity] will be closed.
     */
    open val isRootFragment = false

    /** The OPTIONAL [MenuRes] of the Options Menu for our [Fragment] */
    @get: MenuRes open val menuRes: Int? = null

    /**
     * This value will contains a [String] for the title we will set to the Toolbar in our `Activity`.
     * If this value is not overridden, we call [getString] on [titleRes].
     * @see titleRes
     */
    open val title: String? get() = titleRes?.let { getString( it ) }

    /**
     * The [ColorInt] of [title].
     * Default: if [backgroundColor] is not null, [Color.WHITE] else [android.R.attr.colorPrimary].
     */
    @get: ColorInt open val titleColor: Int get() =
        backgroundColor?.let { Color.WHITE } ?: getThemeColor( android.R.attr.colorPrimary )

    /**
     * This value will contains a [StringRes] for the title we will set to the Toolbar in our `Activity`.
     * If this value is not overridden, we return [R.string.empty].
     * This value will be used from [title].
     * DO NOT CALL IT DIRECTLY, SINCE [title] CAN OVERRIDE IT!
     */
    @get: StringRes open val titleRes: Int? get() = R.string.empty

    /**
     * When is requested to create an Options Menu ( if [menuRes] is not null ), call
     * [MenuInflater.inflate] with our [menuRes] and the received [menu].
     *
     * [menuRes] cannot be null, since this method will be called only if [menuRes] is not null.
     */
    override fun onCreateOptionsMenu( menu: Menu, inflater: MenuInflater ) {
        inflater.inflate( menuRes!!, menu )
    }

    /** A class containing params for configure a `FloatingActionButton` */
    data class FabParams(
            @DrawableRes internal val drawableRes: Int,
            @StringRes internal val textRes: Int,
            internal val showOnStart: Boolean = true,
            internal val action: (View) -> Unit
    )
}

/**
 * A base class for a [Fragment] that is nested inside another [ParentFragment]
 * Inherit from [NavFragment]
 */
abstract class NestedFragment<ParentFragment: NavFragment>(@LayoutRes layoutRes: Int ): NavFragment( layoutRes ) {

    /** @return the [getParentFragment] casted as [ParentFragment] */
    @Suppress("UNCHECKED_CAST")
    val parentNavFragment get() = requireParentFragment() as ParentFragment
}