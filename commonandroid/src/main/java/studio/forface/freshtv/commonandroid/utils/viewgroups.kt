package studio.forface.freshtv.commonandroid.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

/*
 * Author: Davide Giuseppe Farella.
 * An utility file for Android's ViewGroup's.
 */

/**
 * @return a [View] from the inflate a [resource] inside a [ViewGroup].
 *
 * @param attachToRoot default is `false`
 *
 * @see LayoutInflater.from
 * @see LayoutInflater.inflate
 */
fun ViewGroup.inflate( @LayoutRes resource: Int, attachToRoot: Boolean = false ): View =
    LayoutInflater.from( context ).inflate( resource,this, attachToRoot )

/**
 * Create a [View] from the given [resource] and [addView]
 *
 * @param attachToRoot default is `false`
 * @param index default is `-1`
 *
 * @see ViewGroup.inflate
 * @see ViewGroup.addView
 */
fun ViewGroup.addView( @LayoutRes resource: Int, attachToRoot: Boolean = false, index: Int = -1 ) {
    val view = inflate( resource, attachToRoot )
    addView( view, index )
}
