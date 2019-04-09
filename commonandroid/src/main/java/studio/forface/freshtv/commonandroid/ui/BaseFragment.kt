package studio.forface.freshtv.commonandroid.ui

import android.os.Bundle
import android.view.InflateException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import studio.forface.freshtv.commonandroid.frameworkcomponents.AndroidUiComponent
import studio.forface.theia.dsl.TheiaFragment
import studio.forface.viewstatestore.ViewStateFragment

/**
 * A base class for [Fragment]'s.
 *
 * @param layoutRes The [LayoutRes] of the Layout we will inflate in our [Fragment]
 *
 * Inherit from [TheiaFragment].
 * Implements [AndroidUiComponent]
 * Implements [ViewStateFragment]
 *
 * @author Davide Giuseppe Farella
 */
abstract class BaseFragment( @LayoutRes private val layoutRes: Int ) :
    TheiaFragment(), AndroidUiComponent, ViewStateFragment {

    /** On [onCreateView] we inflate the [layoutRes] into the [container] */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = try {
        inflater.inflate( layoutRes, container,false )
    } catch ( e: InflateException ) {
        throw InflateException( "Cannot inflate ${this::class.qualifiedName}", e )
    }
}