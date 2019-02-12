package studio.forface.freshtv.commonandroid.frameworkcomponents

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import studio.forface.freshtv.commonandroid.R
import studio.forface.freshtv.commonandroid.utils.getColor
import studio.forface.freshtv.commonandroid.utils.getThemeColor
import studio.forface.freshtv.commonandroid.viewstate.ViewStateObserver
import studio.forface.freshtv.commonandroid.viewstate.ViewStateStore

/**
 * @author Davide Giuseppe Farella.
 * A base class for [Fragment]'s.
 * The class is sealed since we don't want other implementation that the ones declared here.
 *
 * @param layoutRes The [LayoutRes] of the Layout we will inflate in our [Fragment]
 *
 * Inherit from [Fragment].
 * Implements [AndroidUiComponent]
 */
sealed class BaseFragment( @LayoutRes private val layoutRes: Int ): Fragment(), AndroidUiComponent {

    /** On [onCreateView] we inflate the [layoutRes] into the [container] */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate( layoutRes, container,false )

    /** The [Fragment]'s [NavController] */
    val navController: NavController by lazy { findNavController() }
}

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

    /** Call [ViewStateStore.observe] with [Fragment.getViewLifecycleOwner] as [LifecycleOwner] */
    inline fun <V> ViewStateStore<V>.observe(block: ViewStateObserver<V>.() -> Unit  ) =
        observe( viewLifecycleOwner, block )
}

/**
 * A base class for a [Fragment] that is nested inside another [RootFragment]
 * Inherit from [BaseFragment]
 */
abstract class NestedFragment( @LayoutRes layoutRes: Int ): BaseFragment( layoutRes ) {

}