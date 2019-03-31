package studio.forface.freshtv.commonandroid.frameworkcomponents

import androidx.fragment.app.Fragment

/**
 * An interface for [RotationEnabled] to be implemented by [Fragment]
 * 
 * @author Davide Giuseppe Farella
 */
interface RotationEnabledFragment : RotationEnabled {

    /**
     * Initialize the delegate
     *
     * NOTE: To call when [Fragment] is resumed!
     */
    fun initRotationEnabledFragmentDelegate( fragment: Fragment )
}