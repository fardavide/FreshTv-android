package studio.forface.freshtv.interactors

import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.domain.usecases.AddEpg
import studio.forface.freshtv.domain.usecases.RemoveEpg
import studio.forface.freshtv.domain.usecases.UpdateEpg

/**
 * @author Davide Giuseppe Farella
 * An Interactor for Add or Update a Source File
 */
internal interface AbsEditSourceFileInteractor {

    /** Add a Source File with the given [path], [type] and [name] */
    fun add( path: String, type: SourceFile.Type, name: String? )

    /** Remove the Source File with the given [path] */
    fun remove( path: String )

    /** Update a Source File with the given [path] and [name] */
    fun update( path: String, name: String? )
}