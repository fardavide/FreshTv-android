package studio.forface.freshtv.interactors

import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.domain.usecases.AddEpg
import studio.forface.freshtv.domain.usecases.RemoveEpg
import studio.forface.freshtv.domain.usecases.UpdateEpg

/**
 * @author Davide Giuseppe Farella
 * An Interactor for Add or Update an `EPG`
 */
internal class EditEpgInteractor(
        private val addEpg: AddEpg,
        private val removeEpg: RemoveEpg,
        private val updateEpg: UpdateEpg
) {
    /** Add an `EPG` with the given [path], [type] and [name] */
    fun add( path: String, type: SourceFile.Type, name: String? ) {
        addEpg( path, type, name ?: path )
    }

    /** Remove the `EPG` with the given [path] */
    fun remove( path: String ) {
        removeEpg( path )
    }

    /** Add an `EPG` with the given [path] and [name] */
    fun update( path: String, name: String? ) {
        updateEpg( path, name )
    }
}