package studio.forface.freshtv.domain.usecases

import org.threeten.bp.LocalDateTime
import studio.forface.freshtv.domain.entities.TvGuide
import studio.forface.freshtv.domain.gateways.AppSettings
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella.
 * A class for delete all the old [TvGuide]s
 */
class DeleteOldGuides( private val localData: LocalData, private val settings: AppSettings ) {

    /**
     * Delete all the [TvGuide]s before the given [LocalDateTime]
     * @param before default is a week ago.
     */
    operator fun invoke(
        before: LocalDateTime = LocalDateTime.now().minusDays( settings.oldGuidesLifespanDays )
    ) {
        localData.deleteTvGuidesBefore( before )
    }
}