package studio.forface.freshtv.interactors

import studio.forface.freshtv.domain.usecases.*

/**
 * An Interactor for save the Name of the last `Group` selected
 *
 * @author Davide Giuseppe Farella
 */
internal interface SaveLastChannelInteractor {

    /** Save the given [channelId] and [position] as last channel */
    fun saveLastChannelIdAndPosition( channelId: String?, position: Int )
}

/**
 * An Interactor for save the Name of the last Movie's `Group` selected
 * Inherit from [SaveLastChannelInteractor]
 *
 * @author Davide Giuseppe Farella
 */
internal class SaveLastMovieChannelInteractor(
    private val saveLastMovieChannelIdAndPosition: SaveLastMovieChannelIdAndPosition
) : SaveLastChannelInteractor {

    /** Save the given [channelId] and [position] as last channel */
    override fun saveLastChannelIdAndPosition( channelId: String?, position: Int ) {
        saveLastMovieChannelIdAndPosition( channelId, position )
    }
}

/**
 * An Interactor for save the Name of the last Tv's `Group` selected
 * Inherit from [SaveLastChannelInteractor]
 *
 * @author Davide Giuseppe Farella
 */
internal class SaveLastTvChannelInteractor(
    private val saveLastTvChannelIdAndPosition: SaveLastTvChannelIdAndPosition
) : SaveLastChannelInteractor {

    /** Save the given [channelId] and [position] as last channel */
    override fun saveLastChannelIdAndPosition( channelId: String?, position: Int ) {
        saveLastTvChannelIdAndPosition( channelId, position )
    }
}