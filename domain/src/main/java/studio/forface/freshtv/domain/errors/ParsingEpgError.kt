package studio.forface.freshtv.domain.errors

/**
 * @author Davide Giuseppe Farella
 * A class representing an error of the parsing of a tv guide
 */
data class ParsingEpgError(val rawChannel: String, val reason: Reason ) {

    /** An enum class of reasons for [ParsingEpgError] */
    enum class Reason {

        // TODO
    }
}