@file:Suppress("EXPERIMENTAL_API_USAGE")

package studio.forface.freshtv.parsers.epg

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.domain.entities.TvGuide
import studio.forface.freshtv.domain.errors.ParsingEpgError
import studio.forface.freshtv.domain.utils.ago
import studio.forface.freshtv.domain.utils.forEachAsync
import studio.forface.freshtv.domain.utils.hours
import studio.forface.freshtv.parsers.epg.ParsableEpgItem.Result
import java.io.InputStream

/**
 * @author Davide Giuseppe Farella.
 * A class that parse a [String] EPG
 */
internal class EpgParser {

    /** A [LocalDateTime] representing the time before that the Guides will be skipped */
    private val timeFilter by lazy { ( 12 hours ago ).invoke() }

    /** Parse the [epgContent] and submit items via the given [SendChannel]s */
    suspend operator fun invoke(
        epgContent: String,
        guides: SendChannel<TvGuide>,
        errors: SendChannel<ParsingEpgError>
    ) = coroutineScope<Unit> {

        val epg = ParsableStringEpg( epgContent )
        epg.extractItems()
            .forEachAsync { handleResult( it(), guides, errors ) }

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

        launch {
            for ( item in items ) { handleResult( item(), guides, errors ) }
        }

        launch {
            epg.extractItems( items )

            guides.close()
            errors.close()
        }
    }

    /** Handle [ParsableEpgItem.Result] and send to the correct [SendChannel] */
    private suspend fun handleResult(
        result: Result,
        guides: SendChannel<TvGuide>,
        errors: SendChannel<ParsingEpgError>
    ) {
        when ( result ) {
            is Result.Guide -> {
                if ( result.content.endTime > timeFilter )
                    guides.send( result.content )
            }
            is Result.Error -> errors.send( result.error )
        }
    }
}