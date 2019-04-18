package studio.forface.freshtv.presenters

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.map
import studio.forface.freshtv.commonandroid.mappers.map
import studio.forface.freshtv.domain.entities.ChannelGroup
import studio.forface.freshtv.domain.usecases.GetLastMovieChannelGroupName
import studio.forface.freshtv.domain.usecases.GetLastTvChannelGroupName
import studio.forface.freshtv.domain.usecases.GetMovieChannelGroups
import studio.forface.freshtv.domain.usecases.GetTvChannelGroups
import studio.forface.freshtv.mappers.ChannelGroupsUiModelMapper
import studio.forface.freshtv.uimodels.ChannelGroupsUiModel

/**
 * A Presenter for get `Channel`s Groups
 *
 * @author Davide Giuseppe Farella
 */
internal abstract class ChannelGroupsPresenter(
    private val mapper: ChannelGroupsUiModelMapper
) {
    /** @return the last Group's name shown in the UI */
    protected abstract val lastGroupName: String?

    /** @return [ReceiveChannel] of a [List] of [ChannelGroup] entities */
    protected abstract suspend fun rawGroups() : ReceiveChannel<List<ChannelGroup>>

    /** @return [ReceiveChannel] of [ChannelGroupsUiModel] */
    suspend operator fun invoke() = rawGroups()
        .map { it to lastGroupName }
        .map( mapper ) { it.toUiModel() }
}

/**
 * A Presenter for get `MovieChannel`s Groups
 * Inherit from [ChannelGroupsPresenter]
 *
 * @author Davide Giuseppe Farella
 */
internal class MovieChannelGroupsPresenter(
    private val getMovieGroups: GetMovieChannelGroups,
    private val getLastMovieChannelGroupName: GetLastMovieChannelGroupName,
    mapper: ChannelGroupsUiModelMapper
) : ChannelGroupsPresenter( mapper ) {

    /** @return the last Movie Group's name shown in the UI */
    override val lastGroupName get() = getLastMovieChannelGroupName()

    /** @return [ReceiveChannel] of a [List] of [ChannelGroup] entities for Movie */
    override suspend fun rawGroups() = getMovieGroups.observe()
}

/**
 * A Presenter for get `TvChannel`s Groups
 * Inherit from [ChannelGroupsPresenter]
 *
 * @author Davide Giuseppe Farella
 */
internal class TvChannelGroupsPresenter(
    private val getTvGroups: GetTvChannelGroups,
    private val getLastTvChannelGroupName: GetLastTvChannelGroupName,
    mapper: ChannelGroupsUiModelMapper
) : ChannelGroupsPresenter( mapper ) {

    /** @return the last Tv Group's name shown in the UI */
    override val lastGroupName get() = getLastTvChannelGroupName()

    /** @return [ReceiveChannel] of a [List] of [ChannelGroup] entities for Tv */
    override suspend fun rawGroups() = getTvGroups.observe()
}