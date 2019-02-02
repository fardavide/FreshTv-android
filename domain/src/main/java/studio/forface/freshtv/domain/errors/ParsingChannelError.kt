package studio.forface.freshtv.domain.errors

/**
 * @author Davide Giuseppe Farella
 * A class representing an error of the parsing of a channel
 */
data class ParsingChannelError( val rawChannel: String, val reason: Reason ) {

    /** An enum class of reasons for [ParsingChannelError] */
    enum class Reason {

        /** The channel requires an external player */
        ExternalPlayerRequired,

        /** The channel doesn't have a link */
        MissingLink,

        /** Both name and id are missing in the channel */
        NoNameAndId,

        /** The link of the channel has a "plugin://" schema */
        PluginSchema,

        /** The channel has a wrong format */
        WrongFormat
    }
}