@file:Suppress("EXPERIMENTAL_API_USAGE") // Channel.invokeOnClose

package studio.forface.freshtv.domain.gateways

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.reflect.KProperty

/**
 * @author Davide Giuseppe Farella
 * A class for manage app's Settings
 */
interface AppSettings {

    /** An OPTIONAL [String] representing the name of the last selected `MovieChannel`s `Group` */
    var lastMovieChannelGroupName: String?

    /** An OPTIONAL [String] representing the id of the last `MovieChannel` shown in the center of the screen */
    var lastMovieChannelId: String?

    /** An OPTIONAL [String] representing the name of the last selected `TvChannel`s `Group` */
    var lastTvChannelGroupName: String?

    /** An OPTIONAL [String] representing the id of the last `TvChannel` shown in the center of the screen */
    var lastTvChannelId: String?

    /** A [Boolean] representing whether the night mode is enabled */
    var nightMode: Boolean // TODO Build use case

    /** A [Long] representing the days before an old Guide will be deleted */
    var oldGuidesLifespanDays: Long

    /**
     * Add a listener for the given [KProperty]
     * @return [SettingsListener]
     */
    fun <T : Any> addListener( property: KProperty<T>, block: (T) -> Unit ) : SettingsListener

    /** Remove the given [SettingsListener] */
    fun removeListener( listener: SettingsListener )

    // TODO enum class UiMode { Light, Dark, System, Battery, Schedule }

    /**
     * A listener for the [AppSettings]
     * @property delegate the platform specific listener
     */
    abstract class Listener<D>( val delegate: D )
}

/** A typealias for generic [AppSettings.Listener] */
typealias SettingsListener = AppSettings.Listener<*>

/** @return a [ReceiveChannel] of [T] that will contains the updated values */
fun <T : Any> AppSettings.observe(property: KProperty<T> ): ReceiveChannel<T> {
    val channel = Channel<T>( CONFLATED )
    val listener = addListener( property ) { channel.offer( it ) }

    // Lambda that removes the listener for channel.invokeOnClose
    val removeListener : ( cause: Throwable? ) -> Unit = {
        removeListener( listener )
    }

    channel.invokeOnClose( removeListener )
    return channel
}