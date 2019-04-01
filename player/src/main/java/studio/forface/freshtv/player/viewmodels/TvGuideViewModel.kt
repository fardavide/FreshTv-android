package studio.forface.freshtv.player.viewmodels

import kotlinx.coroutines.launch
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.player.presenters.TvGuidePresenter
import studio.forface.freshtv.player.uiModels.TvProgramUiModel
import studio.forface.viewstatestore.LockedViewStateStore
import studio.forface.viewstatestore.ViewStateStore

/**
 * A `ViewModel` for get Tv's Guide
 *
 * Inherit from [ScopedViewModel]
 *
 * @author Davide Giuseppe Farella
 */
internal class TvGuideViewModel(
    private val guideId: String,
    presenter: TvGuidePresenter
) : ScopedViewModel() {

    /** A [LockedViewStateStore] of [TvProgramUiModel] */
    val program = ViewStateStore<TvProgramUiModel>().lock

    init {
        launch {
            runCatching { presenter( guideId ) }
                .onSuccess { program.postData( it ) }
                .onFailure { program.postError( it ) }
        }
    }
}