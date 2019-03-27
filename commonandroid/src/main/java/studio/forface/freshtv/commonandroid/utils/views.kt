package studio.forface.freshtv.commonandroid.utils

import android.content.Context
import android.view.View
import androidx.annotation.StringRes

/*
 * Author: Davide Giuseppe Farella.
 * An utility file for Android's View's.
 */

/**
 * @return a [String] from [View]
 *
 * @see Context.getString
 */
fun View.getString( @StringRes resId: Int ): String = context.getString( resId )

/**
 * @return a [CharSequence] from [View]
 *
 * @see Context.getText
 */
fun View.getText( @StringRes resId: Int ): CharSequence = context.getText( resId )