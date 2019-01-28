package studio.forface.freshtv.domain.entities

/**
 * @author Davide Giuseppe Farella
 * An entity representing a Group of Channels.
 */
data class ChannelGroup (

    /** The [String] name of the Group */
    val name: String,

    /** The Type of the Group */
    val type: Type,

    /** An OPTIONAL [Url] representing the url for an image ( logo ) of the Group */
    val imageUrl: Url? = null
) {

    /** @return an id created by [ChannelGroup.type] and [ChannelGroup.name] */
    val id get() = "${type.name.toLowerCase()}_$name"

    /**
     * An operator function for merge 2 [ChannelGroup] entities. e.g. `group1 + group2`
     * @return this [ChannelGroup] merged with the given [newGroup]
     */
    operator fun plus( newGroup: ChannelGroup ) = copy(
        name =      name,
        imageUrl =  imageUrl?.validOrNull() ?: newGroup.imageUrl
    )

    /** An enum for the types of the Group */
    enum class Type { MOVIE, TV }
}