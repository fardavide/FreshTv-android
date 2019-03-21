@file:Suppress("unused")

package studio.forface.freshtv.domain.utils

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/** @see Iterable.filter where [predicate]s are called asynchronously */
suspend fun <T> Iterable<T>.filterAsync( predicate: suspend (T) -> Boolean ) = coroutineScope {
    map { it to async { predicate( it ) } } // map to ITEM 'to' deferred PREDICATE
        .filter { it.second.await() } // await and evaluate PREDICATE
        .map { it.first } // re-map to ITEM
}

/** @see Iterable.forEach where [block]s are called asynchronously */
suspend fun <T> Iterable<T>.forEachAsync( block: suspend (T) -> Unit ) = coroutineScope {
    map { async { block( it ) } }.forEach { it.await() }
}

/** @see Iterable.map where [mapper]s are called asynchronously */
suspend fun <T, R> Iterable<T>.mapAsync( mapper: suspend (T) -> R ) = coroutineScope {
    map { async { mapper( it ) } }.map { it.await() }
}

/** @return [Iterable.reduce] if [Iterator.hasNext], else `null` */
internal inline fun <S, T : S> Iterable<T>.reduceOrNull( operation: (acc: S, T) -> S ): S? =
        if ( iterator().hasNext() ) reduce( operation ) else null

/** @return [Iterable.reduce] if [Iterator.hasNext], else `[default] */
internal inline fun <S, T : S> Iterable<T>.reduceOrDefault( default: S, operation: (acc: S, T) -> S ): S =
        if ( iterator().hasNext() ) reduce( operation ) else default

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