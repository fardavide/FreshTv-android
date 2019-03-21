package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.ChannelGroup
import studio.forface.freshtv.domain.gateways.LocalData
import studio.forface.freshtv.domain.utils.filterAsync

/**
 * @author Davide Giuseppe Farella
 * A class for remove empty [ChannelGroup]s
 */
class RemoveEmptyGroups( private val localData: LocalData ) {

    /** Delete the stored [ChannelGroup] that has not channel related */
    suspend operator fun invoke() {
        localData.allGroups().filterAsync { group ->
            localData.allChannels( group.name ).isEmpty()
        }.forEach { localData.deleteGroup( it.id ) }
    }
}