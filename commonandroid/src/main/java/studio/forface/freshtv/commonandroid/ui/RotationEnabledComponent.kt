package studio.forface.freshtv.commonandroid.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * An interface for [RotationEnabled] to be implemented by UI Element
 * 
 * @author Davide Giuseppe Farella
 */
interface RotationEnabledComponent : RotationEnabled {

    /**
     * Initialize this delegate with [FragmentActivity]
     *
     * NOTE: To call when the UI Element is resumed!
     */
    fun initRotationEnabledDelegate( activity: FragmentActivity )

    /**
     * Initialize this delegate with [Fragment]
     *
     * NOTE: To call when the UI Element is resumed!
     */
    fun initRotationEnabledDelegate( fragment: Fragment ) {
        initRotationEnabledDelegate( fragment.requireActivity() )
    }
}