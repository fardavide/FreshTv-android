package studio.forface.freshtv.localdata.sources

import studio.forface.freshtv.localdata.PlaylistPojo
import studio.forface.freshtv.localdata.PlaylistQueries

/**
 * @author Davide Giuseppe Farella.
 * A source for Playlists stored locally
 */
class PlaylistsLocalSource( private val queries: PlaylistQueries) {

    /** @return all the stored playlists [PlaylistPojo] */
    fun all() = queries.selectAll().executeAsList()

    /** Create a new guide [PlaylistPojo] */
    fun createPlaylist( playlist: PlaylistPojo) {
        with( playlist ) {
            queries.insert( path, type, name )
        }
    }

    /** Delete all the stored playlists [PlaylistPojo] */
    fun deleteAll() {
        queries.deleteAll()
    }

    /** Delete the stored playlist [PlaylistPojo] with the given [PlaylistPojo.path] */
    fun delete( path: String ) {
        queries.delete( path )
    }

    /** @return the [PlaylistPojo] with the given [path] */
    fun playlist( path: String ) = queries.selectByPath( path ).executeAsOne()

    /** Update an already stored playlist [PlaylistPojo] */
    fun updatePlaylist( playlist: PlaylistPojo ) {
        with( playlist ) {
            queries.update( path, name )
        }
    }
}