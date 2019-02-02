package studio.forface.freshtv.parsers.epg

import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import studio.forface.freshtv.domain.entities.TvGuide
import studio.forface.freshtv.domain.errors.ParsingTvGuideError

/**
 * @author Davide Giuseppe Farella.
 * A class that parse a [String] EPG
 */
internal class EpgParser {

    /** Parse the [epgContent] and submit items via the given [SendChannel]s */
    suspend operator fun invoke(
        epgPath: String,
        epgContent: String,
        guides: SendChannel<TvGuide>,
        errors: SendChannel<ParsingTvGuideError>
    ) = coroutineScope<Unit> {



        guides.close()
        errors.close()
        TODO("EPG parser not implemented")
    }
}