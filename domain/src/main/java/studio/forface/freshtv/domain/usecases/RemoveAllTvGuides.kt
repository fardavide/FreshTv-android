package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella
 * A class for remove all `Tv Guide`s
 */
class RemoveAllTvGuides( private val localData: LocalData ) {

    /** Delete all the stored `Tv Guide`s */
    operator fun invoke() {
        localData.deleteAllTvGuides()
    }
}