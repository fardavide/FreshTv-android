package studio.forface.freshtv.domain.usecases

import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.domain.entities.TvGuide
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella.
 * A class for delete all the old [TvGuide]s
 */
class DeleteOldGuides( private val localData: LocalData ) {

    /**
     * Delete all the [TvGuide]s before the given [LocalDateTime]
     * @param before default is a week ago.
     */
    operator fun invoke( before: LocalDateTime = LocalDateTime.now().minusWeeks(1 ) ) {
        localData.deleteTvGuidesBefore( before )
    }
}