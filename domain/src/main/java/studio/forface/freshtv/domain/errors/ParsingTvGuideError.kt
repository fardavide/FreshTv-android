package studio.forface.freshtv.domain.errors

/**
 * @author Davide Giuseppe Farella
 * A class representing an error of the parsing of a tv guide
 */
data class ParsingTvGuideError( val rawChannel: String, val reason: Reason ) {

    /** An enum class of reasons for [ParsingTvGuideError] */
    enum class Reason {

        // TODO
    }
}