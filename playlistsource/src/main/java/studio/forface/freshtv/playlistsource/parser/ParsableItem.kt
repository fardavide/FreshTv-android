@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package studio.forface.freshtv.playlistsource.parser

import studio.forface.freshtv.domain.entities.*
import studio.forface.freshtv.domain.errors.ParsingChannelError
import studio.forface.freshtv.domain.errors.ParsingChannelError.Reason
import studio.forface.freshtv.domain.utils.notBlankOrNull

/**
 * @author Davide Giuseppe Farella.
 * An inline class that represents the content of a single item in Playlist and that will result a [ParsableItem.Result]
 * containing the entity if success, or error.
 */
internal inline class ParsableItem( private val s: String ) {

    private companion object {

        /** A [String] representing the param name of the id */
        const val PARAM_ID = "tvg-id"

        /** A [String] representing the param name of the group */
        const val PARAM_GROUP = "group-title"

        /** A [String] representing the param name of the logo */
        const val PARAM_LOGO = "tvg-logo"
    }

    /** TODO */
    private fun channelType() = ChannelType.Tv

    /** @return a [Result.Error] with [s] and the given [Reason] */
    private fun e( reason: Reason ) = Result( ParsingChannelError( s, reason ) )

    /** @return a [ParsableItem.Result] containing the entity just created if success or the error */
    operator fun invoke( playlistPath: String ): Result {

        val lines = s.lines()
        // Return if lines are less than 2
        if ( lines.size < 2 )
            return e( Reason.MissingLink )

        val params = lines[0]

        val link = lines[1]
        // Return if link has 'plugin://' schema
        if ( link.startsWith("plugin" ) )
            return e( Reason.PluginSchema )
        // Return if link has parameters
        if ( link.split( " " ).size > 1 )
            return e( Reason.ExternalPlayerRequired )

        val tempId = s.extract( PARAM_ID )
        val tempName = params.substringAfterLast(',' ).notBlankOrNull()
        val groupName = s.extract( PARAM_GROUP ) ?: IChannel.NO_GROUP_NAME
        val imageUrl = s.extract( PARAM_LOGO )?.let { Url( it ).validOrNull<Url>() }

        // Return if both tempId and tempName are null
        if ( tempId == null && tempName == null )
            return e( Reason.NoNameAndId )

        val id = ( tempId ?: tempName )!!.toLowerCase()
        val name = ( tempName ?: tempId )!!

        // TODO playlistPath
        val content = when( channelType() ) {
            ChannelType.Movie -> MovieChannel( id, name, groupName, imageUrl, link, playlistPath )
            ChannelType.Tv -> TvChannel( id, name, groupName, imageUrl, link, playlistPath )
        }
        return Result( content )
    }

    /** @return an OPTIONAL [String] extracted from the receiver [String] with the given [paramName] */
    private fun String.extract( paramName: String ): String? {
        if ( ! this.contains( paramName ) ) return null
        val indexOfParam = indexOf( paramName )
        val param = substring( indexOfParam ).split('"' )[1]
        return if ( param.isBlank() ) null else param
    }

    /** An enum for the type of [IChannel] */
    private enum class ChannelType { Movie, Tv }

    /** A sealed class for the result of [ParsableItem] */
    internal sealed class Result {

        companion object {
            /**
             * A constructor for [Result]
             * @return [Result.Channel] or [Result.Group]
             */
            operator fun invoke( content: Any ) = when ( content ) {
                is IChannel -> Channel( content )
                is ChannelGroup -> Group( content )
                is ParsingChannelError -> Error( content )
                else -> throw AssertionError( "${content::class.qualifiedName} not implemented" )
            }
        }

        /** A subclass for [Result] containing an [IChannel] */
        class Channel( val content: IChannel ): Result()

        /** A subclass for [Result] containing an [ChannelGroup] */
        class Group( val content: ChannelGroup ): Result()

        /** A subclass of [Result] containing a [ParsingChannelError] */
        class Error( val error: ParsingChannelError): Result()
    }
}