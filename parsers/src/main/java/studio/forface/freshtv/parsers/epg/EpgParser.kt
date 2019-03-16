@file:Suppress("EXPERIMENTAL_API_USAGE")

package studio.forface.freshtv.parsers.epg

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import studio.forface.freshtv.domain.entities.TvGuide
import studio.forface.freshtv.domain.errors.ParsingEpgError
import studio.forface.freshtv.domain.utils.forEachAsync
import studio.forface.freshtv.domain.utils.wait
import studio.forface.freshtv.parsers.SizedStream
import studio.forface.freshtv.parsers.epg.ParsableEpgItem.Result
import java.io.InputStream

/**
 * @author Davide Giuseppe Farella.
 * A class that parse a [String] EPG
 */
internal class EpgParser {

    /** Parse the [epgContent] and submit items via the given [SendChannel]s */
    suspend operator fun invoke(
        epgContent: String,
        guides: SendChannel<TvGuide>,
        errors: SendChannel<ParsingEpgError>
    ) = coroutineScope<Unit> {

        val epg = ParsableStringEpg( epgContent )
        epg.extractItems().forEachAsync {
            when ( val result = it() ) {
                is Result.Guide -> guides.send( result.content )
                is Result.Error -> errors.send( result.error )
            }
        }

        guides.close()
        errors.close()
    }

    /** Parse the [epgStream] and submit items via the given [SendChannel]s */
    suspend operator fun invoke(
        epgStream: SizedStream,
        guides: SendChannel<TvGuide>,
        errors: SendChannel<ParsingEpgError>,
        progress: SendChannel<Int>? = null
    ) = coroutineScope<Unit> {

        val items = Channel<ParsableEpgItem>()
        val progressChannel = Channel<Int>( CONFLATED )
        val epg = ParsableEpg( epgStream )

        launch {
            for ( item in items ) {
                when ( val result = item() ) {
                    is Result.Guide -> guides.send( result.content )
                    is Result.Error -> errors.send( result.error )
                }
            }
        }
        launch {
            for ( p in progressChannel ) progress?.send( p )
        }

        launch {
            epg.extractItems( items, progressChannel )

            guides.close()
            errors.close()
            progress?.close()
        }
    }
}