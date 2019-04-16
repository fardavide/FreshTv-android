package studio.forface.freshtv.preferences.presenters

import org.koin.core.KoinComponent
import org.koin.core.get

/**
 * A Presenter for get Preferences
 *
 * Implements [CleanChannelsPresenter]
 * Implements [CleanGuidesPresenter]
 *
 * @author Davide Giuseppe Farella
 */
internal class PreferencesPresenter :
    CleanChannelsPresenter by Delegates.get(),
    CleanGuidesPresenter by Delegates.get(),
    ToggleNightModePresenter by Delegates.get() {

    /** Clean the presenter */
    fun clean() {
        removeNightModeListener()
    }
}

/** Invoke operator for [PreferencesPresenter] */
internal suspend operator fun <T> PreferencesPresenter.invoke( block: suspend PreferencesPresenter.() -> T ) =
        block()

/** A [KoinComponent] for get Delegates for [PreferencesPresenter] */
private object Delegates : KoinComponent