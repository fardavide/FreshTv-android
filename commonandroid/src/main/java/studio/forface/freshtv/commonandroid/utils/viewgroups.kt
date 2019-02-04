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
 * @param attachToRoot default is False.
 *
 * @see LayoutInflater.from
 * @see LayoutInflater.inflate
 */
fun ViewGroup.inflate( @LayoutRes resource: Int, attachToRoot: Boolean = false ): View =
        LayoutInflater.from( context ).inflate( resource,this, attachToRoot )
