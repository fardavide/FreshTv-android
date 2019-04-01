package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.TvGuide
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella
 * Get a current [TvGuide] for `TvChannel`
 */
class GetCurrentTvGuide( private val localData: LocalData ) {

    /**
     * @return an OPTIONAL [TvGuide] from the `TvChannel` with the given [channelId].
     * It will query from `now` to 10 seconds later and pick the last result, for avoid to pick
     * a [TvGuide] near to finish
     */
    operator fun invoke( channelId: String ) = localData.tvGuideForChannel( channelId )
}