package studio.forface.freshtv.androiddatabase.sqldelight.sources

import androidx.paging.DataSource
import com.squareup.sqldelight.android.paging.QueryDataSourceFactory
import studio.forface.freshtv.androiddatabase.sources.PagedSourceFilesLocalSource
import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.localdata.sqldelight.SourceFilePojo
import studio.forface.freshtv.localdata.sqldelight.SourceFileQueries

/**
 * @author Davide Giuseppe Farella.
 * A source for Tv Channels stored locally, with Paging
 *
 * Inherit from [PagedSourceFilesLocalSource]
 */
class DelightPagedSourceFilesLocalSource(
    private val queries: SourceFileQueries
) : PagedSourceFilesLocalSource<SourceFilePojo> {

    /** @return [DataSource.Factory] for all the stored Epgs [SourceFilePojo] */
    override fun allEpgs(): DataSource.Factory<Int, SourceFilePojo> = QueryDataSourceFactory(
        queryProvider = { l, o -> queries.selectAllByTypePaged( SourceFile.Epg.TYPE_NAME, l, o ) },
        countQuery = queries.countByType( SourceFile.Epg.TYPE_NAME )
    )

    /** @return [DataSource.Factory] for all the stored Playlists [SourceFilePojo] */
    override fun allPlaylists(): DataSource.Factory<Int, SourceFilePojo> = QueryDataSourceFactory(
        queryProvider = { l, o -> queries.selectAllByTypePaged( SourceFile.Playlist.TYPE_NAME, l, o ) },
        countQuery = queries.countByType( SourceFile.Playlist.TYPE_NAME )
    )
}