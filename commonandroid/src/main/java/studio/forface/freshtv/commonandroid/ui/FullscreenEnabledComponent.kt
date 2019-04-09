package studio.forface.freshtv.commonandroid.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * An interface for [FullscreenEnabled] to be implemented by UI Element
 * 
 * @author Davide Giuseppe Farella
 */
interface FullscreenEnabledComponent : FullscreenEnabled {

    /**
     * Initialize the delegate with [FragmentActivity]
     *
     * NOTE: To call when UI Element is resumed!
     */
    fun initFullscreenEnabledDelegate( activity: FragmentActivity )

    /**
     * Initialize the delegate with [Fragment]
     *
     * NOTE: To call when UI Element is resumed!
     */
    fun initFullscreenEnabledDelegate( fragment: Fragment ) {
        initFullscreenEnabledDelegate( fragment.requireActivity() )
    }
}