@file:Suppress("unused")

package studio.forface.freshtv.localdata.sqldelight.utils

import com.squareup.sqldelight.Query
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.map
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

/*
 * Source: https://github.com/JakeWharton/SdkSearch/blob/master/sqldelight-coroutines-extensions/src/main/kotlin/com/squareup/sqldelight/runtime/coroutines/Channels.kt
 */

fun <T : Any> Query<T>.asChannel(): ReceiveChannel<Query<T>> {
    val channel = Channel<Query<T>>( CONFLATED )
    // Ensure consumers immediately run the query.
    channel.offer(this )

    val listener = object : Query.Listener, (Throwable?) -> Unit {
        override fun queryResultsChanged() {
            channel.offer(this@asChannel )
        }

        override fun invoke( cause: Throwable? ) {
            removeListener(this )
        }
    }

    addListener( listener )
    channel.invokeOnClose( listener )

    return channel
}

fun <T : Any> ReceiveChannel<Query<T>>.mapToOne( context: CoroutineContext ) =
    map( context ) { it.executeAsOne() }

suspend fun <T : Any> ReceiveChannel<Query<T>>.mapToOne( context: CoroutineContext? = null ) =
    map(context ?: coroutineContext ) { it.executeAsOne() }

fun <T : Any> ReceiveChannel<Query<T>>.mapToList( context: CoroutineContext ) =
    map( context ) { it.executeAsList() }

suspend fun <T : Any> ReceiveChannel<Query<T>>.mapToList( context: CoroutineContext? = null ) =
    map(context ?: coroutineContext ) { it.executeAsList() }

fun <T : Any> ReceiveChannel<Query<T>>.mapToOneOrNull( context: CoroutineContext ) =
    map( context ) { it.executeAsOneOrNull() }

suspend fun <T : Any> ReceiveChannel<Query<T>>.mapToOneOrNull( context: CoroutineContext? = null ) =
    map(context ?: coroutineContext ) { it.executeAsOneOrNull() }