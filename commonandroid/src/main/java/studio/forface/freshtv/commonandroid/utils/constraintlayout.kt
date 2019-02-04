package studio.forface.freshtv.commonandroid.utils

import android.content.Context
import android.transition.TransitionManager
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

/*
 * Author: Davide Giuseppe Farella.
 * A file containing utilities for ConstraintLayout
 */

/** @constructor for [ConstraintSet] created by [ConstraintSet.clone] with the given [Context] and [LayoutRes] */
@Suppress("FunctionName")
fun ConstraintSet( context: Context, @LayoutRes layoutRes: Int ) =
    ConstraintSet().apply { clone( context, layoutRes ) }

/** [TransitionManager.beginDelayedTransition] and apply the [ConstraintSet] to the given [ConstraintLayout] */
fun ConstraintSet.applyWithTransition( layout: ConstraintLayout ) {
    TransitionManager.beginDelayedTransition( layout )
    applyTo( layout )
}