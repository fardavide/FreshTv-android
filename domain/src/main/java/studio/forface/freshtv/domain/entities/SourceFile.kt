package studio.forface.freshtv.domain.entities

import java.io.Serializable

/**
 * @author Davide Giuseppe Farella
 * An entity for a File Source
 */
sealed class SourceFile {

    /**
     * A [String] representing the identifier path of the this [SourceFile]
     * It will be an url if [type] is [Type.REMOTE] or a path to the Uri if [type] is [Type.LOCAL]
     */
    abstract val path: String

    /** The [Type] for this [SourceFile]. [Type.LOCAL] or [Type.REMOTE] */
    abstract val type: Type

    /** An OPTIONAL [String] name for this [SourceFile] */
    abstract val name: String?

    data class Epg(
        override val path: String,
        override val type: Type,
        override val name: String? = null
    ): SourceFile() {

        companion object { const val TYPE_NAME = "Epg" }
        /**
         * An operator function for merge 2 [Epg] entities. e.g. `guide1 + guide2`
         * @return this [Epg] merged with the given [newEpg]
         */
        operator fun plus( newEpg: Epg ) = copy(
            name = newEpg.name
        )
    }

    data class Playlist(
        override val path: String,
        override val type: Type,
        override val name: String? = null
    ): SourceFile() {

        companion object { const val TYPE_NAME = "Playlist" }
        /**
         * An operator function for merge 2 [Playlist] entities. e.g. `playlist1 + playlist2`
         * @return this [Playlist] merged with the given [newPlaylist]
         */
        operator fun plus( newPlaylist: Playlist ) = copy(
            name = newPlaylist.name
        )
    }

    /** An enum for the types of Playlist */
    enum class Type : Serializable { LOCAL, REMOTE }
}