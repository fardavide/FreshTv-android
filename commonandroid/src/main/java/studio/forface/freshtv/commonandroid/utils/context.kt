package studio.forface.freshtv.commonandroid.utils

import android.content.Context
import android.os.Handler

/*
 * Author: Davide Giuseppe Farella.
 * This file contains utilities for Android's Context.
 */

/** Execute an [action] on the [Context.getMainLooper] ( UI Thread. ) */
inline fun Context.postOnUi( crossinline action: () -> Unit ) =
    Handler( mainLooper ).post{ action() }