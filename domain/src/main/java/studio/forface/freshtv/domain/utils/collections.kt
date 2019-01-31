package studio.forface.freshtv.domain.utils

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/** @see Iterable.forEach where [block]s are called asynchronously */
suspend fun <T> Iterable<T>.forEachAsync( block: suspend (T) -> Unit ) = coroutineScope {
    map { async { block( it ) } }.forEach { it.await() }
}

/** Replace an [old] [E] in the [List] with a [new] [E].
 * * if [old] is null, [MutableList.add] [new]
 * * if [List.contains] [old], [MutableList.set] [new] with [List.indexOf] [old]
 * * else do nothing
 */
internal fun <E> MutableList<E>.replace( old: E?, new: E ) {
    when {
        old == null -> add( new )
        contains( old ) -> set( indexOf( old ), new )
        else -> { /* Do nothing */ }
    }
}

/** @see MutableList.replace */
internal operator fun <E> MutableList<E>.set( old: E?, new: E ) = replace( old, new )