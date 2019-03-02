package studio.forface.freshtv.commonandroid.utils

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment

/*
 * Author: Davide Giuseppe Farella.
 * Utils for the Android's FragmentManager.
 */

/**
 * We use this extension function for execute some action when a [Fragment] is resumed.
 *
 * @param action the action to be executed when a [Fragment] is resumed.
 *
 * @return the [FragmentManager.FragmentLifecycleCallbacks] that needs to be unregistered later
 * with [FragmentManager.unregisterFragmentLifecycleCallbacks].
 *
 * @see FragmentManager.registerFragmentLifecycleCallbacks
 */
inline fun FragmentManager.onFragmentResumed (
    crossinline action: (Fragment) -> Unit
): FragmentManager.FragmentLifecycleCallbacks {
    val listener = object : FragmentManager.FragmentLifecycleCallbacks() {

        override fun onFragmentResumed( fragmentManager: FragmentManager, fragment: Fragment ) {
            super.onFragmentResumed( fragmentManager, fragment )
            if ( fragment !is NavHostFragment ) action( fragment )
        }
    }
    registerFragmentLifecycleCallbacks( listener,true )
    return listener
}

/**
 * @param filterNavHost if `true` [NavHostFragment] will be filtered.
 * Default is `true`
 *
 * @param block a lambda with [FragmentLifecycleObserver] as receiver
 *
 * @return the [FragmentManager.FragmentLifecycleCallbacks] that needs to be unregistered later
 * with [FragmentManager.unregisterFragmentLifecycleCallbacks].
 *
 * @see FragmentManager.registerFragmentLifecycleCallbacks
 */
inline fun FragmentManager.onFragmentLifecycle (
    filterNavHost: Boolean = true,
    block: FragmentLifecycleObserver.() -> Unit
): FragmentManager.FragmentLifecycleCallbacks {

    val observer = FragmentLifecycleObserver().apply( block )
    val listener = FragmentLifecycleObserverInvoker( observer, filterNavHost )
    registerFragmentLifecycleCallbacks( listener,true )
    return listener
}

/**
 * A class that observes the `Lifecycle` of a [Fragment].
 *
 * Call the functions relative to the `Lifecycle` event for execute an action when the event occurs.
 * I.e. `onCreated { fragment, savedInstanceState -> doSomeAction() }`
 */
@Suppress("unused")
class FragmentLifecycleObserver @PublishedApi internal constructor(): FragmentManager.FragmentLifecycleCallbacks() {

    /* Lambdas called on relative lifecycle event. On default they're empty and they'll be set by relative function */
    private var onFragmentPreAttached:          FragmentLifecycleCallback = {}
    private var onFragmentAttached:             FragmentLifecycleCallback = {}
    private var onFragmentPreCreated:           OptionalBundleFragmentLifecycleCallback = { _, _ -> }
    private var onFragmentCreated:              OptionalBundleFragmentLifecycleCallback = { _, _ -> }
    private var onFragmentActivityCreated:      OptionalBundleFragmentLifecycleCallback = { _, _ -> }
    private var onFragmentViewCreated:          ViewFragmentLifecycleCallback = { _, _, _ -> }
    private var onFragmentStarted:              FragmentLifecycleCallback = {}
    private var onFragmentResumed:              FragmentLifecycleCallback = {}
    private var onFragmentPaused:               FragmentLifecycleCallback = {}
    private var onFragmentStopped:              FragmentLifecycleCallback = {}
    private var onFragmentSaveInstanceState:    BundleFragmentLifecycleCallback = { _, _ -> }
    private var onFragmentViewDestroyed:        FragmentLifecycleCallback = {}
    private var onFragmentDestroyed:            FragmentLifecycleCallback = {}
    private var onFragmentDetached:             FragmentLifecycleCallback = {}

    internal fun FragmentLifecycleObserverInvoker.callPreAttached( fragment: Fragment ) {
        onFragmentPreAttached( fragment )
    }
    internal fun FragmentLifecycleObserverInvoker.callAttached( fragment: Fragment ) {
        onFragmentAttached( fragment )
    }
    internal fun FragmentLifecycleObserverInvoker.callPreCreated( fragment: Fragment, savedInstanceState: Bundle? ) {
        onFragmentPreCreated( fragment, savedInstanceState )
    }
    internal fun FragmentLifecycleObserverInvoker.callCreated( fragment: Fragment, savedInstanceState: Bundle? ) {
        onFragmentCreated( fragment, savedInstanceState )
    }
    internal fun FragmentLifecycleObserverInvoker.callActivityCreated( fragment: Fragment, savedInstanceState: Bundle? ) {
        onFragmentActivityCreated( fragment, savedInstanceState )
    }
    internal fun FragmentLifecycleObserverInvoker.callViewCreated( fragment: Fragment, view: View, savedInstanceState: Bundle? ) {
        onFragmentViewCreated( fragment, view, savedInstanceState )
    }
    internal fun FragmentLifecycleObserverInvoker.callStarted( fragment: Fragment ) {
        onFragmentStarted( fragment )
    }
    internal fun FragmentLifecycleObserverInvoker.callResumed( fragment: Fragment ) {
        onFragmentResumed( fragment )
    }
    internal fun FragmentLifecycleObserverInvoker.callPaused( fragment: Fragment ) {
        onFragmentPaused( fragment )
    }
    internal fun FragmentLifecycleObserverInvoker.callStopped( fragment: Fragment ) {
        onFragmentStopped( fragment )
    }
    internal fun FragmentLifecycleObserverInvoker.callSaveInstanceState( fragment: Fragment, outState: Bundle ) {
        onFragmentSaveInstanceState( fragment, outState )
    }
    internal fun FragmentLifecycleObserverInvoker.callViewDestroyed( fragment: Fragment ) {
        onFragmentViewDestroyed( fragment )
    }
    internal fun FragmentLifecycleObserverInvoker.callDestroyed( fragment: Fragment ) {
        onFragmentDestroyed( fragment )
    }
    internal fun FragmentLifecycleObserverInvoker.callDetached( fragment: Fragment ) {
        onFragmentDetached( fragment )
    }

    /* Functions for the the lambdas */
    fun onPreAttached( action: FragmentLifecycleCallback ) {
        onFragmentPreAttached = action
    }
    fun onAttached( action: FragmentLifecycleCallback ) {
        onFragmentAttached = action
    }
    fun onPreCreated( action: OptionalBundleFragmentLifecycleCallback ) {
        onFragmentPreCreated = action
    }
    fun onCreated( action: OptionalBundleFragmentLifecycleCallback ) {
        onFragmentCreated = action
    }
    fun onActivityCreated( action: OptionalBundleFragmentLifecycleCallback ) {
        onFragmentActivityCreated = action
    }
    fun onViewCreated( action: ViewFragmentLifecycleCallback ) {
        onFragmentViewCreated = action
    }
    fun onStarted( action: FragmentLifecycleCallback ) {
        onFragmentStarted = action
    }
    fun onResumed( action: FragmentLifecycleCallback ) {
        onFragmentResumed = action
    }
    fun onPaused( action: FragmentLifecycleCallback ) {
        onFragmentPaused = action
    }
    fun onStopped( action: FragmentLifecycleCallback ) {
        onFragmentStopped = action
    }
    fun onSaveInstanceState( action: BundleFragmentLifecycleCallback ) {
        onFragmentSaveInstanceState = action
    }
    fun onViewDestroyed( action: FragmentLifecycleCallback ) {
        onFragmentViewDestroyed = action
    }
    fun onDestroyed( action: FragmentLifecycleCallback ) {
        onFragmentDestroyed = action
    }
    fun onDetached( action: FragmentLifecycleCallback ) {
        onFragmentDetached = action
    }
}

/**
 * A [FragmentManager.FragmentLifecycleCallbacks] that will invoke lambdas on its [FragmentLifecycleObserver]
 *
 * @param observer the [FragmentLifecycleObserver]
 *
 * @param filterNavHost if `true` [NavHostFragment] will be filtered
 */
@PublishedApi
internal class FragmentLifecycleObserverInvoker(
    private val observer: FragmentLifecycleObserver,
    private val filterNavHost: Boolean
) : FragmentManager.FragmentLifecycleCallbacks() {

    /** @return `true` is [filterNavHost] is disabled or [fragment] is NOT [NavHostFragment] */
    private fun canRun( fragment: Fragment ) = ! filterNavHost || fragment !is NavHostFragment

    /* Override FragmentManager.FragmentLifecycleCallbacks invoking relative lambdas */
    override fun onFragmentPreAttached( fragmentManager: FragmentManager, fragment: Fragment, context: Context ) {
        if ( canRun( fragment ) ) with( observer ) { callPreAttached( fragment ) }
    }
    override fun onFragmentAttached( fragmentManager: FragmentManager, fragment: Fragment, context: Context ) {
        if ( canRun( fragment ) ) with( observer ) { callAttached( fragment ) }
    }
    override fun onFragmentPreCreated( fragmentManager: FragmentManager, fragment: Fragment, savedInstanceState: Bundle? ) {
        if ( canRun( fragment ) ) with( observer ) { callPreCreated( fragment, savedInstanceState ) }
    }
    override fun onFragmentCreated( fragmentManager: FragmentManager, fragment: Fragment, savedInstanceState: Bundle? ) {
        if ( canRun( fragment ) ) with( observer ) { callCreated( fragment, savedInstanceState ) }
    }
    override fun onFragmentActivityCreated( fragmentManager: FragmentManager, fragment: Fragment, savedInstanceState: Bundle? ) {
        if ( canRun( fragment ) ) with( observer ) { callActivityCreated( fragment, savedInstanceState ) }
    }
    override fun onFragmentViewCreated( fragmentManager: FragmentManager, fragment: Fragment, view: View, savedInstanceState: Bundle? ) {
        if ( canRun( fragment ) ) with( observer ) { callViewCreated( fragment, view, savedInstanceState ) }
    }
    override fun onFragmentStarted( fragmentManager: FragmentManager, fragment: Fragment ) {
        if ( canRun( fragment ) ) with( observer ) { callStarted( fragment ) }
    }
    override fun onFragmentResumed( fragmentManager: FragmentManager, fragment: Fragment ) {
        if ( canRun( fragment ) ) with( observer ) { callResumed( fragment ) }
    }
    override fun onFragmentPaused( fragmentManager: FragmentManager, fragment: Fragment ) {
        if ( canRun( fragment ) ) with( observer ) { callPaused( fragment ) }
    }
    override fun onFragmentStopped( fragmentManager: FragmentManager, fragment: Fragment ) {
        if ( canRun( fragment ) ) with( observer ) { callStopped( fragment ) }
    }
    override fun onFragmentSaveInstanceState( fragmentManager: FragmentManager, fragment: Fragment, outState: Bundle ) {
        if ( canRun( fragment ) ) with( observer ) { callSaveInstanceState( fragment, outState ) }
    }
    override fun onFragmentViewDestroyed( fragmentManager: FragmentManager, fragment: Fragment ) {
        if ( canRun( fragment ) ) with( observer ) { callViewDestroyed( fragment ) }
    }
    override fun onFragmentDestroyed( fragmentManager: FragmentManager, fragment: Fragment ) {
        if ( canRun( fragment ) ) with( observer ) { callDestroyed( fragment ) }
    }
    override fun onFragmentDetached( fragmentManager: FragmentManager, fragment: Fragment ) {
        if ( canRun( fragment ) ) with( observer ) { callDetached( fragment ) }
    }
}

/* Action typealias' */
typealias FragmentLifecycleCallback = (fragment: Fragment) -> Unit
typealias BundleFragmentLifecycleCallback = (fragment: Fragment, outState: Bundle) -> Unit
typealias OptionalBundleFragmentLifecycleCallback = (fragment: Fragment, savedInstanceState: Bundle?) -> Unit
typealias ViewFragmentLifecycleCallback = (fragment: Fragment, view: View, savedInstanceState: Bundle?) -> Unit