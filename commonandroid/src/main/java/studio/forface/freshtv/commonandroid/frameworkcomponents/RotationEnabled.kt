package studio.forface.freshtv.commonandroid.frameworkcomponents

import android.content.pm.ActivityInfo

/**
 * An interface for an UI item that can be rotated.
 * 
 * @author Davide Giuseppe Farella
 */
interface RotationEnabled : AndroidComponent {

    /** The current [Rotation] */
    val currentRotation: Rotation

    /** The initial [Rotation] when the UI item is created or displayed */
    var initialRotation: Rotation?

    /**
     * A [Rotation] value representing the last rotation set through [rotate].
     * We use this for avoid it set the same rotation more that once.
     */
    var lastRotation: Rotation?

    /** @return whether the device is currently in landscape mode */
    fun isLandscape(): Boolean

    /**
     * Restore the initial [Rotation] by calling [rotate] with [initialRotation].
     *
     * @throws IllegalStateException if [initialRotation] is null.
     */
    fun restoreInitialRotation() {
        initialRotation?.let { rotate( it ) }
                ?: throw IllegalStateException(
                        "${::initialRotation.name} has not been initialized yet."
                )
    }

    /** Rotate the UI item if the [rotation] is different from [lastRotation] */
    fun rotate( rotation: Rotation )

    /**
     * Set [initialRotation] by getting the actual rotation of the UI item and match to
     * [Rotation].
     */
    fun setInitialRotation()

    /**
     * An enum that wraps [ActivityInfo] rotation [Int] values.
     */
    enum class Rotation( val value: Int ) {
        LANDSCAPE(          ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ),
        PORTRAIT(           ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ),
        REVERSE_LANDSCAPE(  ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE ),
        REVERSE_PORTRAIT(   ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT ),
        UNSPECIFIED(        ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED ),
        USER(               ActivityInfo.SCREEN_ORIENTATION_USER );

        companion object {
            /**
             * @return a [Rotation] entry from a [value].
             * If not [Rotation] entry is found, log an [IllegalArgumentException] and return
             * [PORTRAIT]
             *
             * @param value the [Int] value to match to [Rotation.value].
             */
            fun fromValue( value: Int, onError: (Throwable) -> Unit ): Rotation {
                val found = Rotation.values().find { it.value == value }
                found ?: onError( IllegalArgumentException(
                        "Cannot find a ${Rotation::class.simpleName} entry from the given value: $value"
                ) )
                return found ?: PORTRAIT
            }
        }
    }
}