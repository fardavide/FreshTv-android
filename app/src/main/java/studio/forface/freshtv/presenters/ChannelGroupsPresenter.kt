package studio.forface.freshtv.presenters

import studio.forface.freshtv.domain.usecases.GetTvChannelGroups
import studio.forface.freshtv.mappers.ChannelGroupUiModelMapper
import studio.forface.freshtv.commonandroid.mappers.map
import studio.forface.freshtv.domain.usecases.GetMovieChannelGroups
import studio.forface.freshtv.uimodels.ChannelGroupUiModel

/**
 * @author Davide Giuseppe Farella
 * A Presenter for get `TvChannel`s Groups
 */
internal class ChannelGroupsPresenter(
        private val getMovieGroups: GetMovieChannelGroups,
        private val getTvGroups: GetTvChannelGroups,
        private val mapper: ChannelGroupUiModelMapper
) {

    /** @return a [List] of [ChannelGroupUiModel] for Movie */
    fun movie() = getMovieGroups().map( mapper ) { it.toUiModel() }

    /** @return a [List] of [ChannelGroupUiModel] for Tv */
    fun tv() = getTvGroups().map( mapper ) { it.toUiModel() }
}