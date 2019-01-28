package studio.forface.freshtv.domain.utils

/** @return null if the receiver [CharSequence.isBlank] else the receiver itself */
fun CharSequence.notBlankOrNull() = if ( isBlank() ) null else this

/** @return null if the receiver [String.isBlank] else the receiver itself */
fun String.notBlankOrNull() = if ( isBlank() ) null else this