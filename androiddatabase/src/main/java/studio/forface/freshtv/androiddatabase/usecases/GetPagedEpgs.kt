package studio.forface.freshtv.androiddatabase.usecases

import androidx.paging.DataSource
import studio.forface.freshtv.androiddatabase.localdata.PagedLocalData
import studio.forface.freshtv.domain.entities.SourceFile.Epg

/**
 * @author Davide Giuseppe Farella
 * Get the available [Epg]s with Paging support
 */
class GetPagedEpgs internal constructor( private val localData: PagedLocalData ) {

    /** @return a [DataSource] of available [Epg]s */
    operator fun invoke() = localData.epgs()
}