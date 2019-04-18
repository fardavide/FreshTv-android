package studio.forface.freshtv.presenters

import androidx.paging.DataSource
import studio.forface.freshtv.androiddatabase.usecases.GetPagedTvChannelsWithGuide
import studio.forface.freshtv.mappers.TvChannelUiModelMapper
import studio.forface.freshtv.commonandroid.mappers.map
import studio.forface.freshtv.domain.entities.ChannelIdAndPosition
import studio.forface.freshtv.domain.usecases.GetLastTvChannelIdAndPosition
import studio.forface.freshtv.uimodels.TvChannelUiModel

/**
 * A Presenter for get `TvChannel`s
 *
 * @author Davide Giuseppe Farella
 */
internal class TvChannelsPresenter(
    private val getPagedTvChannels: GetPagedTvChannelsWithGuide,
    private val getLastTvChannelIdAndPosition: GetLastTvChannelIdAndPosition,
    private val mapper: TvChannelUiModelMapper
) {

    /** @return OPTIONAL last [ChannelIdAndPosition] */
    fun getLastPosition() = getLastTvChannelIdAndPosition()

    /**
     * @return a [DataSource] of [TvChannelUiModel]
     * @param groupName OPTIONAL [String] for filter results by `Channel`s group name.
     */
    operator fun invoke( groupName: String ) = getPagedTvChannels( groupName )
            .map( mapper ) { it.toUiModel() }
}