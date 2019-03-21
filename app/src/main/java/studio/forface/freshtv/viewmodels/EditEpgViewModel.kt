package studio.forface.freshtv.viewmodels

import androidx.lifecycle.ViewModel
import studio.forface.freshtv.interactors.EditEpgInteractor
import studio.forface.freshtv.presenters.EpgPresenter
import studio.forface.freshtv.services.RefreshTvGuidesWorker

/**
 * @author Davide Giuseppe Farella
 * A [ViewModel] get and edit an `EPG`
 *
 * Inherit from [AbsEditSourceFileViewModel]
 */
internal class EditEpgViewModel(
        interactor: EditEpgInteractor,
        presenter: EpgPresenter,
        epgPath: String?
) : AbsEditSourceFileViewModel( interactor, presenter, epgPath ) {

    /** Enqueue [RefreshTvGuidesWorker] for the given [filePath] */
    override fun enqueueWorker( filePath: String ) {
        RefreshTvGuidesWorker.enqueue( filePath )
    }

    /** Cancel [RefreshTvGuidesWorker] for the given [filePath] */
    override fun cancelWorker( filePath: String ) {
        RefreshTvGuidesWorker.cancel( filePath )
    }
}