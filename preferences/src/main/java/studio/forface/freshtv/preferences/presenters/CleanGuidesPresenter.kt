package studio.forface.freshtv.preferences.presenters

import kotlinx.coroutines.channels.ReceiveChannel
import studio.forface.freshtv.commonandroid.mappers.invoke
import studio.forface.freshtv.commonandroid.mappers.map
import studio.forface.freshtv.domain.usecases.HasTvGuides
import studio.forface.freshtv.preferences.mappers.GuidesDatabaseStateUiModelMapper
import studio.forface.freshtv.preferences.uimodels.GuidesDatabaseStateUiModel

/**
 * A Presenter for get Preferences for Clean `Tv Guide`s
 *
 * @author Davide Giuseppe Farella
 */
internal interface CleanGuidesPresenter {

    /** @return [GuidesDatabaseStateUiModel] */
    fun guidesDatabaseState(): GuidesDatabaseStateUiModel

    /** @return [ReceiveChannel] of [GuidesDatabaseStateUiModel] */
    suspend fun observeGuidesDatabaseState() : ReceiveChannel<GuidesDatabaseStateUiModel>
}

/** Implementation of [CleanChannelsPresenter] */
internal class CleanGuidesPresenterImpl(
    private val hasTvGuides: HasTvGuides,
    private val mapper: GuidesDatabaseStateUiModelMapper
) : CleanGuidesPresenter {

    /** @return [GuidesDatabaseStateUiModel] */
    override fun guidesDatabaseState(): GuidesDatabaseStateUiModel = mapper { hasTvGuides().toUiModel() }

    /** @return [ReceiveChannel] of [GuidesDatabaseStateUiModel] */
    override suspend fun observeGuidesDatabaseState() =
            hasTvGuides.observe().map( mapper ) { it.toUiModel() }
}