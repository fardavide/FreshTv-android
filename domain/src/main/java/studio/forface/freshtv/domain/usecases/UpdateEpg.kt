package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.SourceFile.Epg
import studio.forface.freshtv.domain.gateways.LocalData
import studio.forface.freshtv.domain.gateways.updateEpg

/**
 * @author Davide Giuseppe Farella
 * A class for update an [Epg]
 */
class UpdateEpg( private val localData: LocalData ) {

    /** Change the [Epg.path] and [Epg.name] of the given [Epg] */
    operator fun invoke( epg: Epg, name: String? ) {
        this( epg.path, name )
    }

    /** Change the [Epg.path] and [Epg.name] of the [Epg] with the given [epgPath] */
    operator fun invoke( epgPath: String, name: String? ) {
        localData.updateEpg( epgPath ) { it.copy( path = epgPath, name = name ) }
    }
}