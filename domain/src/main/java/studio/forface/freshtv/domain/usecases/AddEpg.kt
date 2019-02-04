package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.domain.entities.SourceFile.Epg
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella
 * A class for Add a Playlist from a Local or Remote Source.
 */
class AddEpg(
    private val localData: LocalData
) {

    /** Add [Epg] with the given [path], [type] and optional [name] */
    operator fun invoke( path: String, type: SourceFile.Type, name: String = path ) {
        val epg = Epg( path, type, name )
        localData.storeEpg( epg )
    }
}