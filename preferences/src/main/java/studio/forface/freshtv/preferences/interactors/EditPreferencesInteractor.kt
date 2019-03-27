package studio.forface.freshtv.preferences.interactors

import org.koin.core.KoinComponent
import org.koin.core.get

/**
 * An Interactor for edit Preferences
 *
 * Implements [CleanChannelsInteractor]
 * Implements [CleanGuidesInteractor]
 *
 * @author Davide Giuseppe Farella
 */
internal class EditPreferencesInteractor :
    CleanChannelsInteractor by Delegates.get(),
    CleanGuidesInteractor by Delegates.get()

/** A [KoinComponent] for get Delegates for [EditPreferencesInteractor] */
private object Delegates : KoinComponent