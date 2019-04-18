package studio.forface.freshtv.interactors

import studio.forface.freshtv.domain.usecases.SaveLastMovieChannelGroupName
import studio.forface.freshtv.domain.usecases.SaveLastTvChannelGroupName

/**
 * An Interactor for save the Name of the last `Group` selected
 *
 * @author Davide Giuseppe Farella
 */
internal interface SaveLastGroupInteractor {

    /** Save the given [groupName] as last group name */
    fun saveLastGroupName( groupName: String? )
}

/**
 * An Interactor for save the Name of the last Movie's `Group` selected
 * Inherit from [SaveLastGroupInteractor]
 *
 * @author Davide Giuseppe Farella
 */
internal class SaveLastMovieGroupInteractor(
    private val saveLastMovieChannelGroupName: SaveLastMovieChannelGroupName
) : SaveLastGroupInteractor {

    /** Save the given [groupName] as last Movie's group name */
    override fun saveLastGroupName( groupName: String? ) {
        saveLastMovieChannelGroupName( groupName )
    }
}

/**
 * An Interactor for save the Name of the last Tv's `Group` selected
 * Inherit from [SaveLastGroupInteractor]
 *
 * @author Davide Giuseppe Farella
 */
internal class SaveLastTvGroupInteractor(
    private val saveLastTvChannelGroupName: SaveLastTvChannelGroupName
) : SaveLastGroupInteractor {

    /** Save the given [groupName] as last Tv's group name */
    override fun saveLastGroupName( groupName: String? ) {
        saveLastTvChannelGroupName( groupName )
    }
}