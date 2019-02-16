package studio.forface.freshtv.commonandroid.utils

import android.content.Context
import android.content.ContextWrapper
import android.os.Handler
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/*
 * Author: Davide Giuseppe Farella.
 * This file contains utilities for Android's Context.
 */

/** @return [ColorInt] from the [Context], if pre [Android.MARSHMALLOW] use [ContextCompat] */
@ColorInt fun Context.getColorCompat( @ColorRes id: Int ) =
    if ( Android.MARSHMALLOW ) getColor( id ) else ContextCompat.getColor(this, id )

/** Execute an [action] on the [Context.getMainLooper] ( UI Thread. ) */
inline fun Context.postOnUi( crossinline action: () -> Unit ) =
    Handler( mainLooper ).post{ action() }