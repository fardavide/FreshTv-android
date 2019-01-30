package studio.forface.freshtv.domain.errors

/**
 * @author Davide Giuseppe Farella
 * A class representing an error of the parsing of a channel
 */
data class ParsingChannelError( val rawChannel: String, val reason: Reason ) {

    /** An enum class of reasons for [ParsingChannelError] */
    enum class Reason {
        ExternalPlayerRequired,
        MissingLink,
        NoNameAndId,
        PluginSchema
    }
}