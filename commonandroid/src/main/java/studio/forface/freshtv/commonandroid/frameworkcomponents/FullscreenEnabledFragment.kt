package studio.forface.freshtv.commonandroid.frameworkcomponents

import androidx.fragment.app.Fragment

/**
 * An interface for [FullscreenEnabled] to be implemented by [Fragment]
 * 
 * @author Davide Giuseppe Farella
 */
interface FullscreenEnabledFragment : FullscreenEnabled {

    /**
     * Initialize the delegate
     *
     * NOTE: To call when [Fragment] is resumed!
     */
    fun initFullscreenEnabledFragmentDelegate( fragment: Fragment )
}