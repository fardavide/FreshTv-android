package studio.forface.freshtv.preferences.viewmodels

import kotlinx.coroutines.launch
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.preferences.interactors.EditPreferencesInteractor
import studio.forface.freshtv.preferences.presenters.PreferencesPresenter
import studio.forface.freshtv.preferences.presenters.invoke
import studio.forface.freshtv.preferences.uimodels.ChannelsDatabaseStateUiModel
import studio.forface.freshtv.preferences.uimodels.GuidesDatabaseStateUiModel
import studio.forface.viewstatestore.ViewStateStore
import studio.forface.viewstatestore.postData

/**
 * A `ViewModel` for get and edit Preferences
 *
 * Inherit from [ScopedViewModel]
 *
 * @author Davide Giuseppe Farella
 */
internal class PreferencesViewModel(
    private val presenter: PreferencesPresenter,
    private val interactor: EditPreferencesInteractor
) : ScopedViewModel() {

    /** A [ViewStateStore] for [ChannelsDatabaseStateUiModel] */
    val channelsDatabaseState = ViewStateStore<ChannelsDatabaseStateUiModel>()

    /** A [ViewStateStore] for [GuidesDatabaseStateUiModel] */
    val guidesDatabaseState = ViewStateStore<GuidesDatabaseStateUiModel>()

    init {
        // Observe Channels
        launch {
            for ( state in presenter { observeChannelsDatabaseState() } )
                channelsDatabaseState.postData( state )
        }
        // Observe Guides
        launch {
            for ( state in presenter.observeGuidesDatabaseState() )
                guidesDatabaseState.postData( state )
        }
    }

    /** Clean all the stored `Channel`s and `Group`s */
    fun cleanChannels() {
        launch { interactor.cleanAllChannels() }
    }

    /** Clean all the stored `Tv Guide`s */
    fun cleanGuides() {
        launch { interactor.cleanAllGuides() }
    }
}