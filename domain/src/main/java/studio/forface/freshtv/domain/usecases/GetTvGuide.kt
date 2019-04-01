package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.TvGuide
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella
 * Get [TvGuide] by id
 */
class GetTvGuide( private val localData: LocalData ) {

    /** @return the [TvGuide] with the given [guideId] */
    operator fun invoke( guideId: String ) = localData.tvGuide( guideId )
}