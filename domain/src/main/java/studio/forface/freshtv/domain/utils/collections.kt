package studio.forface.freshtv.domain.utils

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

suspend fun <T> Iterable<T>.forEachAsync( block: suspend (T) -> Unit) = coroutineScope {
    for ( element in this@forEachAsync ) {
        launch { block( element ) }
    }
}