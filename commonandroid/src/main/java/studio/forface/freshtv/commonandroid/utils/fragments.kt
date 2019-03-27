package studio.forface.freshtv.commonandroid.utils

import android.content.res.Resources
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

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

/** Call [LayoutInflater.inflate] using [Fragment.getView] as `parent` param */
fun Fragment.inflate( @LayoutRes resource: Int, attachToRoot: Boolean = false ): View =
        layoutInflater.inflate( resource, view as ViewGroup, attachToRoot )