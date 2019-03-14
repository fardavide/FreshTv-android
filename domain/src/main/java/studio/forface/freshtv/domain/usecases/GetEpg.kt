package studio.forface.freshtv.domain.usecases

import studio.forface.freshtv.domain.entities.SourceFile.Epg
import studio.forface.freshtv.domain.gateways.LocalData

/**
 * @author Davide Giuseppe Farella
 * Get an [Epg]
 */
class GetEpg( private val localData: LocalData ) {

    /**
     * @return a [Epg]
     * @param epgPath the [Epg.path]
     */
    operator fun invoke( epgPath: String ) = localData.epg( epgPath )
}