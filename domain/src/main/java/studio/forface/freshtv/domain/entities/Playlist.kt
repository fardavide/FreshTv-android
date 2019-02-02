package studio.forface.freshtv.domain.entities

/**
 * @author Davide Giuseppe Farella
 * An entity for a Playlist of Channels
 */
data class Playlist (
    val path: String,
    val type: Type,
    val name: String = path
) {

    /**
     * An operator function for merge 2 [Playlist] entities. e.g. `playlist1 + playlist2`
     * @return this [Playlist] merged with the given [newPlaylist]
     */
    operator fun plus( newPlaylist: Playlist ) = copy(
        name = newPlaylist.name
    )

    /** An enum for the types of Playlist */
    enum class Type { LOCAL, REMOTE }
}