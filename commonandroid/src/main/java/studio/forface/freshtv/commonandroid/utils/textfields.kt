package studio.forface.freshtv.commonandroid.utils

import android.text.Editable
import android.text.Spannable
import android.text.TextWatcher
import android.widget.EditText

/*
 * Author: Davide Giuseppe Farella.
 * A file containing utilities for Android's text fields.
 */

/**
 * Observe the [TextWatcher.onTextChanged] on [EditText] from a simple lambda.
 * @return [TextWatcher].
 */
inline fun EditText.onTextChange( crossinline action: (CharSequence) -> Unit ): TextWatcher {
    val textWatcher = object : TextWatcher {
        /**
         * This method is called to notify you that, somewhere within
         * `s`, the text has been changed.
         * It is legitimate to make further changes to `s` from
         * this callback, but be careful not to get yourself into an infinite
         * loop, because any changes you make will cause this method to be
         * called again recursively.
         * (You are not told where the change took place because other
         * afterTextChanged() methods may already have made other changes
         * and invalidated the offsets.  But if you need to know here,
         * you can use [Spannable.setSpan] in [.onTextChanged]
         * to mark your place and then look up from here where the span
         * ended up.
         */
        override fun afterTextChanged( s: Editable) {
            /* DO NOTHING */
        }

        /**
         * This method is called to notify you that, within `s`,
         * the `count` characters beginning at `start`
         * are about to be replaced by new text with length `after`.
         * It is an error to attempt to make changes to `s` from
         * this callback.
         */
        override fun beforeTextChanged( s: CharSequence, start: Int, count: Int, after: Int ) {
            /* DO NOTHING */
        }

        /**
         * This method is called to notify you that, within `s`,
         * the `count` characters beginning at `start`
         * have just replaced old text that had length `before`.
         * It is an error to attempt to make changes to `s` from
         * this callback.
         */
        override fun onTextChanged( s: CharSequence, start: Int, before: Int, count: Int ) {
            action( s )
        }
    }
    addTextChangedListener( textWatcher )
    return textWatcher
}