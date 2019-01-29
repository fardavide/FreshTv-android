package studio.forface.freshtv.domain.utils

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/** @see Iterable.forEach where [block]s are called asynchronously */
suspend fun <T> Iterable<T>.forEachAsync( block: suspend (T) -> Unit ) = coroutineScope {
    map { async { block( it ) } }.forEach { it.await() }
}