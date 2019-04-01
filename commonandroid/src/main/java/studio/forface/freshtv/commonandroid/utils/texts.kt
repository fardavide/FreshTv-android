package studio.forface.freshtv.commonandroid.utils

import android.graphics.Typeface.BOLD
import android.text.Spannable
import android.text.Spannable.SPAN_INCLUSIVE_INCLUSIVE
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan

/** @return a [SpannableString] with [StyleSpan] [BOLD] from the given [CharSequence] */
fun CharSequence.bold() = SpannableString(this )
    .setSpan( StyleSpan( BOLD ),0, lastIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE )

/** @see SpannableStringBuilder.append */
@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
fun SpannableStringBuilder.append(
    text: CharSequence,
    what: Any,
    flags: Int = SPAN_INCLUSIVE_INCLUSIVE
): SpannableStringBuilder = append( text, what, flags )