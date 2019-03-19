package studio.forface.freshtv.presenters

import androidx.paging.DataSource
import studio.forface.freshtv.androiddatabase.usecases.GetPagedTvChannels
import studio.forface.freshtv.androiddatabase.usecases.GetPagedTvChannelsWithGuide
import studio.forface.freshtv.domain.usecases.GetCurrentTvGuide
import studio.forface.freshtv.domain.utils.handle
import studio.forface.freshtv.mappers.TvChannelUiModelMapper
import studio.forface.freshtv.mappers.map
import studio.forface.freshtv.uimodels.TvChannelUiModel

/**
 * @author Davide Giuseppe Farella
 * A Presenter for get `TvChannel`s
 */
internal class TvChannelsPresenter(
        private val getPagedTvChannels: GetPagedTvChannelsWithGuide,
        private val mapper: TvChannelUiModelMapper
) {

    /**
     * @return a [DataSource] of [TvChannelUiModel]
     * @param groupName OPTIONAL [String] for filter results by `Channel`s group name.
     */
    operator fun invoke( groupName: String ) = getPagedTvChannels( groupName )
            .map( mapper ) { it.toUiModel() }
}