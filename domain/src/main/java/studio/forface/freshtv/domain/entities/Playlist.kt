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

    /** An enum for the types of Playlist */
    enum class Type { LOCAL, REMOTE }
}