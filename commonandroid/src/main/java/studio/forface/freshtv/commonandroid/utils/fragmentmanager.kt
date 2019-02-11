package studio.forface.freshtv.commonandroid.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment

/*
 * Author: Davide Giuseppe Farella.
 * An Utility file for the Android's FragmentManager.
 */

/**
 * We use this extension function for execute some action when a [Fragment] is resumed.
 *
 * @param action the action to be executed when a [Fragment] is resumed.
 * @return the [FragmentManager.FragmentLifecycleCallbacks] that needs to be unregistered later
 * with [FragmentManager.unregisterFragmentLifecycleCallbacks].
 *
 * @see FragmentManager.registerFragmentLifecycleCallbacks
 */
inline fun FragmentManager.onFragmentResumed( crossinline action: (Fragment) -> Unit )
        : FragmentManager.FragmentLifecycleCallbacks {
    val listener = object : FragmentManager.FragmentLifecycleCallbacks() {

        override fun onFragmentResumed( fragmentManager: FragmentManager, fragment: Fragment ) {
            super.onFragmentResumed( fragmentManager, fragment )
            if ( fragment !is NavHostFragment ) action( fragment )
        }
    }
    registerFragmentLifecycleCallbacks( listener,true )
    return listener
}
