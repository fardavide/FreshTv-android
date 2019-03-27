package studio.forface.freshtv.preferences.interactors

import studio.forface.freshtv.domain.usecases.RemoveAllTvGuides

/**
 * An Interactor for Clean `Tv Guide`s
 *
 * @author Davide Giuseppe Farella
 */
internal interface CleanGuidesInteractor {

    /** Remove all stored `Tv Guide`s */
    fun cleanAllGuides()
}

/** Implementation of [CleanGuidesInteractor] */
internal class CleanGuidesInteractorImpl(
    private val removeAllGuides: RemoveAllTvGuides
) : CleanGuidesInteractor {

    /** Remove all stored `Channel`s and `Group`s */
    override fun cleanAllGuides() {
        removeAllGuides()
    }
}