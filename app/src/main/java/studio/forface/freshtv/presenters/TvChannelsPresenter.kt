package studio.forface.freshtv.presenters

import androidx.paging.DataSource
import studio.forface.freshtv.androiddatabase.usecases.GetPagedTvChannels
import studio.forface.freshtv.domain.usecases.GetCurrentTvGuide
import studio.forface.freshtv.uimodels.SourceFileUiModel.Playlist
import studio.forface.freshtv.domain.usecases.GetPlaylist
import studio.forface.freshtv.mappers.SourceFileUiModelMapper
import studio.forface.freshtv.mappers.TvChannelUiModelMapper
import studio.forface.freshtv.uimodels.TvChannelUiModel

/**
 * @author Davide Giuseppe Farella
 * A Presenter for get `TvChannel`s
 */
internal class TvChannelsPresenter(
        private val getPagedTvChannels: GetPagedTvChannels,
        private val getCurrentTvGuide: GetCurrentTvGuide,
        private val mapper: TvChannelUiModelMapper
) {

    /**
     * @return a [DataSource] of [TvChannelUiModel]
     * @param groupName OPTIONAL [String] for filter results by `Channel`s group name.
     */
    operator fun invoke( groupName: String? ) =
            ( groupName?.let { getPagedTvChannels( groupName ) } ?: getPagedTvChannels() )
                    .map { mapper { ( it to getCurrentTvGuide( it.id ) ).toUiModel() } }
}