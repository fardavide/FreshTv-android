package studio.forface.freshtv.preferences.interactors

import studio.forface.freshtv.domain.usecases.RemoveAllChannels
import studio.forface.freshtv.domain.usecases.RemoveEmptyGroups

/**
 * An Interactor for Clean Channels
 *
 * @author Davide Giuseppe Farella
 */
internal interface CleanChannelsInteractor {

    /** Remove all stored `Channel`s and `Group`s */
    suspend fun cleanAllChannels()
}

/** Implementation of [CleanChannelsInteractor] */
internal class CleanChannelsInteractorImpl(
    private val removeAllChannels: RemoveAllChannels,
    private val removeEmptyGroups: RemoveEmptyGroups
) : CleanChannelsInteractor {

    /** Remove all stored `Channel`s and `Group`s */
    override suspend fun cleanAllChannels() {
        removeAllChannels()
        removeEmptyGroups()
    }
}