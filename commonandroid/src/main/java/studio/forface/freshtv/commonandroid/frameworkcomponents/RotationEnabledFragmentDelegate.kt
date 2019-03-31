package studio.forface.freshtv.commonandroid.frameworkcomponents

import android.content.res.Configuration
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import studio.forface.freshtv.commonandroid.frameworkcomponents.RotationEnabled.Rotation

/**
 * A Delegate for a [Fragment] that implements [RotationEnabledFragment]
 * 
 * @author Davide Giuseppe Farella
 */
class RotationEnabledFragmentDelegate : RotationEnabledFragment {

    /** A strong reference to the [FragmentActivity] */
    lateinit var activity : FragmentActivity

    /** The current [Rotation] */
    override val currentRotation get() = RotationEnabled.Rotation.fromValue( orientation ) { notifier.error( it ) }

    /** @return the rotation of the [activity] */
    val orientation get() = activity.resources.configuration.orientation

    /**
     * The initial [Rotation] when the UI item is created or displayed
     *
     * Default value is null since we can't declare which will be the initial rotation.
     */
    override var initialRotation: RotationEnabled.Rotation? = null

    /**
     * A [Rotation] value representing the last rotation set through [rotate].
     * We use this for avoid it set the same rotation more that once.
     *
     * Default value is null since we can't declare which is the last rotation before
     * calling [rotate].
     */
    override var lastRotation: RotationEnabled.Rotation? = null

    /**
     * Initialize this delegate
     *
     * NOTE: To call when [Fragment] is resumed!
     */
    override fun initRotationEnabledFragmentDelegate( fragment: Fragment ) {
        activity = fragment.requireActivity()
        setInitialRotation()
    }

    /** @return whether the device is currently in landscape mode */
    override fun isLandscape() = orientation == Configuration.ORIENTATION_LANDSCAPE

    /** Rotate the UI item if the [rotation] is different from [lastRotation] */
    override fun rotate( rotation: RotationEnabled.Rotation ) {
        if ( rotation != lastRotation ) {
            lastRotation = rotation
            activity.requestedOrientation = rotation.value
        }
    }

    /**
     * Set [initialRotation] by getting the actual rotation of the UI item and match to
     * [Rotation].
     */
    override fun setInitialRotation() {
        if ( initialRotation == null )
            initialRotation = currentRotation
    }
}