package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella
 * A class for remove all `Channel`s and `Group`s
 */
class RemoveAllChannels( private val localData: LocalData ) {

    /** Delete all the stored `Channel`s and `Group`s */
    operator fun invoke() {
        localData.deleteAllChannels()
    }
}