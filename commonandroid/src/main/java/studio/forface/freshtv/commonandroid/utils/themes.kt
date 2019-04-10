package studio.forface.freshtv.commonandroid.utils

import android.content.Context
import android.content.res.Resources.Theme
import androidx.fragment.app.FragmentActivity
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment

/*
 * Author: Davide Giuseppe Farella.
 * A file containing utilities for Android's Resources.Theme.
 */

/**
 * @return [ColorInt] for the given [androidColorAttr] from a [Context] for the current [Theme].
 * @param androidColorAttr the attribute of the color to get.
 */
@ColorInt fun Context.getThemeColor(androidColorAttr: Int ): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute( androidColorAttr, typedValue,true )
    return typedValue.data
}

/**
 * @return [ColorInt] for the given [androidColorAttr] from a [Fragment] for the current [Theme].
 * @see FragmentActivity.getThemeColor
 */
@ColorInt fun Fragment.getThemeColor( androidColorAttr: Int ): Int =
        requireActivity().getThemeColor( androidColorAttr )