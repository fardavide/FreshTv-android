@file:Suppress("EXPERIMENTAL_API_USAGE")

package studio.forface.freshtv.parsers.epg

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import studio.forface.freshtv.domain.entities.TvGuide
import studio.forface.freshtv.domain.errors.ParsingEpgError
import studio.forface.freshtv.domain.utils.forEachAsync
import studio.forface.freshtv.domain.utils.wait
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
        epgStream: InputStream,
        guides: SendChannel<TvGuide>,
        errors: SendChannel<ParsingEpgError>
    ) = coroutineScope<Unit> {

        val items = Channel<ParsableEpgItem>()
        val epg = ParsableEpg( epgStream )
        epg.extractItems( items )

        for ( item in items ) {
            when ( val result = item() ) {
                is Result.Guide -> guides.send( result.content )
                is Result.Error -> errors.send( result.error )
            }
        }

        wait { items.isClosedForReceive }

        guides.close()
        errors.close()
    }
}