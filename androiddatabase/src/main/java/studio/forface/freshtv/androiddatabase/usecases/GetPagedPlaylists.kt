package studio.forface.freshtv.androiddatabase.usecases

import androidx.paging.DataSource
import studio.forface.freshtv.androiddatabase.localdata.PagedLocalData
import studio.forface.freshtv.domain.entities.SourceFile.Playlist

/**
 * @author Davide Giuseppe Farella
 * Get the available [Playlist]s with Paging support
 */
class GetPagedPlaylists internal constructor( private val localData: PagedLocalData ) {

    /** @return a [DataSource] of available [Playlist]s */
    operator fun invoke() = localData.playlists()
}