package studio.forface.freshtv.commonandroid.utils

import android.content.res.Resources
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment

/*
 * Author: Davide Giuseppe Farella.
 * A file containing utilities for Fragment's.
 */

/** Get a [ColorInt] color from the [Resources] from a [Fragment] */
@ColorInt fun Fragment.getColor( @ColorRes colorRes: Int ) = context?.getColorCompat( colorRes )