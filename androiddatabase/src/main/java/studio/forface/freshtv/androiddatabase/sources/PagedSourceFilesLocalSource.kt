package studio.forface.freshtv.androiddatabase.sources

import androidx.paging.DataSource
import studio.forface.freshtv.domain.entities.SourceFile

/**
 * @author Davide Giuseppe Farella.
 * A source for [SourceFile]s stored locally, with Paging
 */
interface PagedSourceFilesLocalSource<T> {

    /** @return [DataSource.Factory] for all the stored Epgs [T] */
    fun allEpgs(): DataSource.Factory<Int, T>

    /** @return [DataSource.Factory] for all the stored Playlists [T] */
    fun allPlaylists(): DataSource.Factory<Int, T>
}