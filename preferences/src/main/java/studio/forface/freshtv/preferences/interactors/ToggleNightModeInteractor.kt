package studio.forface.freshtv.preferences.interactors

import studio.forface.freshtv.domain.gateways.AppSettings

/**
 * An Interactor for toggle Night Mode
 *
 * @author Davide Giuseppe Farella
 */
internal interface ToggleNightModeInteractor { // TODO use case

    /** Toggle the Night Mode */
    fun toggleNightMode()
}

/** Implementation of [ToggleNightModeInteractor] */
internal class ToggleNightModeInteractorImpl(
    private val appSettings: AppSettings
) : ToggleNightModeInteractor {

    /** Toggle the Night Mode */
    override fun toggleNightMode() {
        appSettings.nightMode = ! appSettings.nightMode
    }
}