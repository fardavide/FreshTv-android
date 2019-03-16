package studio.forface.freshtv.parsers.utils

import java.io.Reader

/*
 * Utils for kotlin.io.Reader
 */

/** Execute an [action] [forEachLine] of [Reader] only if [filter] is satisfied */
internal inline fun Reader.forEachLine(
    crossinline filter: (String) -> Boolean,
    crossinline action: (String) -> Unit
) {
    forEachLine { if ( filter( it ) ) action( it ) }
}

/**
 * Execute an [action] [forEachLine] of [Reader] only if [filter] is satisfied, also send the index within the
 * [action] lambda
 */
internal inline fun Reader.forEachLineSized(
    crossinline filter: (String) -> Boolean,
    crossinline action: (readSize: Long, line: String) -> Unit
) {
    var readSize = 0L
    forEachLine {
        val stringSize = 36 + it.length * 2
        readSize += stringSize
        if ( filter( it ) ) action( readSize, it )
    }
}