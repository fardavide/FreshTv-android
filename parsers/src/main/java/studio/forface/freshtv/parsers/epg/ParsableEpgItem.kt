@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package studio.forface.freshtv.parsers.epg

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.w3c.dom.Node
import org.xml.sax.SAXParseException
import studio.forface.freshtv.domain.entities.TvGuide
import studio.forface.freshtv.domain.errors.ParsingEpgError
import studio.forface.freshtv.domain.errors.ParsingEpgError.Reason
import studio.forface.freshtv.domain.utils.EMPTY_STRING
import studio.forface.freshtv.domain.utils.optLet
import studio.forface.freshtv.parsers.utils.*

/**
 * @author Davide Giuseppe Farella.
 * An inline class that represents the content of a single item in Playlist and that will return a
 * [ParsableEpgItem.Result] containing the entity if success, or error.
 */
internal inline class ParsableEpgItem( private val s: String ) {

    private companion object {

        /** A [String] representing the param name of the channel id */
        const val PARAM_CHANNEL_ID = "channel"

        /** A [String] representing the param name of the title */
        const val PARAM_TITLE = "title"

        /** A [String] representing the param name of the description] */
        const val PARAM_DESCRIPTION = "desc"

        /** A [String] representing the param name of the image url of the logo */
        const val PARAM_IMAGE_URL = "logo"

        /** A [String] representing the param name of the category */
        const val PARAM_CATEGORY = "category"

        /** A [String] representing the param name of the year of release */
        const val PARAM_YEAR = "year"

        /** A [String] representing the param name of the year of release */
        const val PARAM_DATE = "date"

        /** A [String] representing the param name of the country of release */
        const val PARAM_COUNTRY = "country"

        /** A [String] representing the param name of the credits */
        const val PARAM_CREDITS = "credits"

        /** A [String] representing the param name of the credit's director */
        const val PARAM_DIRECTOR = "director"

        /** A [String] representing the param name of the credit's actors */
        const val PARAM_ACTOR = "actor"

        /** A [String] representing the param name of the rating */
        const val PARAM_RATING = "rating"

        /** A [String] representing the param name of the value of rating */
        const val PARAM_RATING_VALUE = "value"

        /** A [String] representing the param name of the time of start */
        const val PARAM_START_TIME = "start"

        /** A [String] representing the param name of the time of end */
        const val PARAM_END_TIME = "stop"
    }

    /** @return a [Result.Error] with [s] and the given [Reason] */
    private fun e( reason: Reason ) = Result( ParsingEpgError( s, reason ) )

    /**
     * @return a [ParsableEpgItem.Result] containing the entity just created if success or
     * the error
     */
    operator fun invoke(): Result {
        val root = readDocument( s )

        val channelId =     root attr PARAM_CHANNEL_ID
        val credits = optLet( root optChild PARAM_CREDITS ) {
            TvGuide.Credits(
                    it optChildValue PARAM_DIRECTOR,
                    it childListValues PARAM_ACTOR
            )
        }
        val rating = optLet<Node, String>( root optChild PARAM_RATING ) {
            it childValue PARAM_RATING_VALUE
        }

        val rawStartTime = root attr PARAM_START_TIME
        val rawEndTime = root attr PARAM_END_TIME

        val startTime = LocalDateTime.parse( rawStartTime, dtFormatter )
        val endTime = LocalDateTime.parse( rawEndTime, dtFormatter )

        val id = channelId + rawStartTime

        return Result( TvGuide(
                id =            id,
                channelId =     channelId,
                title =         root childValue PARAM_TITLE,
                description =   root optChildValue PARAM_DESCRIPTION ?: EMPTY_STRING,
                imageUrl =      root optChildValue PARAM_IMAGE_URL,
                category =      root optChildValue PARAM_CATEGORY,
                year =          root optChildValue PARAM_YEAR ?: root optChildValue PARAM_DATE,
                country =       root optChildValue PARAM_COUNTRY,
                credits =       credits,
                rating =        rating,
                startTime =     startTime,
                endTime =       endTime
        ) )
    }

    /** A sealed class for the result of [ParsableEpgItem] */
    internal sealed class Result {

        /** A subclass for [Result] containing a [TvGuide] */
        class Guide( val content: TvGuide ): Result()

        /** A subclass for [Result] containing a [ParsingEpgError] */
        class Error( val error: ParsingEpgError ): Result()

        companion object {
            /**
             * A constructor for [Result]
             * @return [Result]
             */
            operator fun invoke( content: Any ) = when ( content ) {
                is TvGuide -> Guide( content )
                is ParsingEpgError -> Error( content )
                else -> throw AssertionError( "${content::class.qualifiedName} not implemented" )
            }
        }

    }
}

/** A [DateTimeFormatter] for [ParsableEpgItem] */
private val dtFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss Z")