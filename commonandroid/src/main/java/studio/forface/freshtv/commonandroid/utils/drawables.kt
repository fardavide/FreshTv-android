package studio.forface.freshtv.commonandroid.utils

import android.content.Context
import android.graphics.drawable.Drawable
import studio.forface.freshtv.commonandroid.R
import studio.forface.materialbottombar.utils.getColorCompat

/**
 * Apply [Drawable.setTint] with [R.color.colorOnSurface] to the receiver [Drawable]
 * @return OPTIONAL [Drawable], `null` if the receiver is `null`
 */
fun Drawable?.colorOnSurface( context: Context ) = this?.apply {
    setTint( context.getColorCompat( R.color.colorOnSurface ) )
}