package studio.forface.freshtv.commonandroid.utils

import android.os.Build

/**
 * @author Davide Giuseppe Farella.
 * An object for Android generic utilities.
 */
object Android {

    /** @return [Boolean] whether the current SDK if equals of greater that Android Marshmallow */
    val MARSHMALLOW get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    /** @return [Boolean] whether the current SDK if equals of greater that Android Oreo */
    val OREO get() =  Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    /** @return [Boolean] whether the current SDK if equals of greater that Android Pie */
    val PIE get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
}