package studio.forface.freshtv.commonandroid.frameworkcomponents

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.annotation.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import studio.forface.freshtv.commonandroid.R
import studio.forface.freshtv.commonandroid.utils.getColor
import studio.forface.freshtv.commonandroid.utils.getThemeColor
import studio.forface.materialbottombar.dsl.MaterialPanel
import studio.forface.materialbottombar.dsl.PanelBuilder
import studio.forface.materialbottombar.dsl.panel
import studio.forface.theia.dsl.TheiaFragment
import studio.forface.viewstatestore.ViewStateFragment

/**
 * @author Davide Giuseppe Farella.
 * A base class for [Fragment]'s.
 * The class is sealed since we don't want other implementation that the ones declared here.
 *
 * @param layoutRes The [LayoutRes] of the Layout we will inflate in our [Fragment]
 *
 * Inherit from [TheiaFragment].
 * Implements [AndroidUiComponent]
 */
sealed class BaseFragment( @LayoutRes private val layoutRes: Int ) :
    TheiaFragment(), AndroidUiComponent, ViewStateFragment {

    /** On [onCreateView] we inflate the [layoutRes] into the [container] */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate( layoutRes, container,false )

    /** The [Fragment]'s [NavController] */
    val navController: NavController by lazy { findNavController() }

    /** Close a [MaterialPanel] and remove it */
    fun dismissAndRemovePanel( panelId: Int ) {
        baseActivity?.dismissAndRemovePanel( panelId )
    }

    /** Close a [MaterialPanel] */
    fun dismissPanel() {
        baseActivity?.dismissPanel()
    }

    /** Shortcut for show a dialog [MaterialPanel] with a static id */
    fun showDialog( builder: PanelBuilder.( View ) -> Unit ) {
        val body = layoutInflater.inflate( R.layout.layout_dialog_body, requireView() as ViewGroup,false )
        val panel = panel { builder( body ).apply {
            customBody( body )
        } }
        showPanel( DIALOG_PANEL_ID, panel )
    }

    /** Add the given [MaterialPanel] and open it. */
    fun showPanel( panelId: Int, panel: MaterialPanel ) {
        baseActivity?.showPanel( panelId, panel )
    }

    /** Add the [MaterialPanel] created through [builder] and open it. */
    fun showPanel( panelId: Int, builder: PanelBuilder.() -> Unit ) {
        showPanel( panelId, panel { builder() } )
    }
}

/** An [Int] ID for [BaseFragment.showPanel] for a generic dialog [MaterialPanel] */
private const val DIALOG_PANEL_ID = 909

/**
 * A base class for a [Fragment] that have an `Activity` as parent
 * Inherit from [BaseFragment]
 */
abstract class RootFragment( @LayoutRes layoutRes: Int ): BaseFragment( layoutRes ) {
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

    /** @return OPTIONAL [BaseActivity.fab] */
    protected val fab get() = baseActivity?.fab

    /** The OPTIONAL [FabParams] for setup a `FloatingActionButton` in the `Activity` */
    open val fabParams: FabParams? = null

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
        backgroundColor?.let { Color.WHITE } ?: getThemeColor( android.R.attr.colorPrimary )!!

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
 * A base class for a [Fragment] that is nested inside another [RootFragment]
 * Inherit from [BaseFragment]
 */
abstract class NestedFragment<ParentFragment: RootFragment>( @LayoutRes layoutRes: Int ): BaseFragment( layoutRes ) {

    /** @return the [getParentFragment] casted as [ParentFragment] */
    @Suppress("UNCHECKED_CAST")
    val rootFragment get() = requireParentFragment() as ParentFragment
}