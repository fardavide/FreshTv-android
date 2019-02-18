package studio.forface.freshtv.localdata.sources

/**
 * @author Davide Giuseppe Farella.
 * A source for Playlists stored locally
 */
interface SourceFilesLocalSource<Pojo> {

    /** @return all the stored Source Files [Pojo] */
    fun all(): List<Pojo>

    /** @return all the stored Epgs [Pojo] */
    fun allEpgs(): List<Pojo>

    /** @return all the stored Playlists [Pojo] */
    fun allPlaylists(): List<Pojo>

    /** Create a new Source File [Pojo] */
    fun create( sourceFile: Pojo )

    /** Delete all the stored Source Files [Pojo] */
    fun deleteAll()

    /** Delete the stored Source Files [Pojo] with the given [Pojo]'s path */
    fun delete( path: String )

    /** @return the Epg [Pojo] with the given [path] */
    fun epg( path: String ): Pojo

    /** @return the Playlist [Pojo] with the given [path] */
    fun playlist( path: String ): Pojo

    /** Update an already stored Source File [Pojo] */
    fun update( sourceFile: Pojo )
}