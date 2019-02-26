package studio.forface.freshtv.commonandroid.utils

import androidx.annotation.StringRes
import androidx.work.Worker

/*
 * Author: Davide Giuseppe Farella
 * Utils for `Worker`s
 */

/** @return a [String] from `Resources` within a [Worker] */
fun Worker.getString( @StringRes id: Int, vararg formatArgs: Any ): String =
    applicationContext.getString( id, *formatArgs )