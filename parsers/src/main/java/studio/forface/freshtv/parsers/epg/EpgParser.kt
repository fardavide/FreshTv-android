package studio.forface.freshtv.parsers.epg

import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import studio.forface.freshtv.domain.entities.TvGuide
import studio.forface.freshtv.domain.errors.ParsingEpgError
import studio.forface.freshtv.domain.utils.forEachAsync
import studio.forface.freshtv.parsers.epg.ParsableEpgItem.Result

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

        val epg = ParsableEpg( epgContent )
        epg.extractItems().forEachAsync {
            when ( val result = it() ) {
                is Result.Guide -> guides.send( result.content )
                is Result.Error -> errors.send( result.error )
            }
        }

        guides.close()
        errors.close()
    }
}