package studio.forface.freshtv.presenters

import studio.forface.freshtv.domain.usecases.GetTvChannelGroups
import studio.forface.freshtv.mappers.TvChannelGroupUiModelMapper
import studio.forface.freshtv.commonandroid.mappers.map
import studio.forface.freshtv.uimodels.TvChannelGroupUiModel

/**
 * @author Davide Giuseppe Farella
 * A Presenter for get `TvChannel`s Groups
 */
internal class TvChannelGroupsPresenter(
        private val getGroups: GetTvChannelGroups,
        private val mapper: TvChannelGroupUiModelMapper
) {

    /** @return a [List] of [TvChannelGroupUiModel] */
    operator fun invoke() = getGroups().map( mapper ) { it.toUiModel() }
}