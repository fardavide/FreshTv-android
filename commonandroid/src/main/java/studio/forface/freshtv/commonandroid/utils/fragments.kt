package studio.forface.freshtv.commonandroid.utils

import android.content.res.Resources
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import android.content.pm.PackageManager

/*
 * Author: Davide Giuseppe Farella.
 * A file containing utilities for Fragment's.
 */

/**
 * @return [Int] representing if the given [permission]
 * @see PackageManager `PERMISSION` constants
 */
fun Fragment.checkSelfPermission( permission: String ) =
        ContextCompat.checkSelfPermission( requireContext(), permission )

/** Get a [ColorInt] color from the [Resources] from a [Fragment] */
@ColorInt fun Fragment.getColor( @ColorRes colorRes: Int ) = context?.getColorCompat( colorRes )