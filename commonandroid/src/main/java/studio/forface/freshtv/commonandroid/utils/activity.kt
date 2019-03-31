package studio.forface.freshtv.commonandroid.utils

import android.view.View
import androidx.core.view.postDelayed
import androidx.fragment.app.FragmentActivity

/** Call [View.postDelayed] fom [FragmentActivity] */
inline fun FragmentActivity.postDelayed( delayInMillis: Long, crossinline action: () -> Unit ) {
    window.decorView.postDelayed( delayInMillis, action )
}