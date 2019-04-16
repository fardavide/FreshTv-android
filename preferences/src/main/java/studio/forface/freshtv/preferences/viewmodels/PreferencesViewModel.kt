package studio.forface.freshtv.preferences.viewmodels

import kotlinx.coroutines.launch
import studio.forface.freshtv.commonandroid.frameworkcomponents.ScopedViewModel
import studio.forface.freshtv.preferences.interactors.EditPreferencesInteractor
import studio.forface.freshtv.preferences.presenters.PreferencesPresenter
import studio.forface.freshtv.preferences.presenters.invoke
import studio.forface.freshtv.preferences.uimodels.ChannelsDatabaseStateUiModel
import studio.forface.freshtv.preferences.uimodels.GuidesDatabaseStateUiModel
import studio.forface.viewstatestore.LockedViewStateStore
import studio.forface.viewstatestore.ViewStateStore

/**
 * A `ViewModel` for get and edit Preferences
 * Inherit from [ScopedViewModel]
 *
 * @author Davide Giuseppe Farella
 */
internal class PreferencesViewModel(
    private val presenter: PreferencesPresenter,
    private val interactor: EditPreferencesInteractor
) : ScopedViewModel() {

    /** A [LockedViewStateStore] for [ChannelsDatabaseStateUiModel] */
    val channelsDatabaseState = ViewStateStore<ChannelsDatabaseStateUiModel>().lock

    /** A [LockedViewStateStore] for [GuidesDatabaseStateUiModel] */
    val guidesDatabaseState = ViewStateStore<GuidesDatabaseStateUiModel>().lock

    /** A [LockedViewStateStore] for [Boolean] */
    val nightModeEnabled = ViewStateStore<Boolean>().lock

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
        // Observe Night Mode
        launch {
            for( state in presenter.observeNightModeEnabled() )
                nightModeEnabled.postData( state )
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

    /** Toggle the Nighe Mode */
    fun toggleNigheMode() {
        interactor.toggleNightMode()
    }

    /** When the `ViewModel` is cleared */
    override fun onCleared() {
        presenter.clean()
        super.onCleared()
    }
}