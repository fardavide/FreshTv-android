package studio.forface.freshtv.playlistsource.playlist

import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import studio.forface.freshtv.domain.entities.ChannelGroup
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.entities.MovieChannel
import studio.forface.freshtv.domain.entities.TvChannel
import studio.forface.freshtv.domain.errors.ParsingChannelError
import studio.forface.freshtv.domain.utils.forEachAsync
import studio.forface.freshtv.playlistsource.playlist.ParsablePlaylistItem.Result

/**
 * @author Davide Giuseppe Farella.
 * A class that parse a [String] playlist
 */
internal class PlaylistParser {

    /** Parse the [playlistContent] and submit items via the given [SendChannel]s */
    @Synchronized
    suspend fun parse(
            playlistPath: String,
            playlistContent: String,
            channels: SendChannel<IChannel>,
            groups: SendChannel<ChannelGroup>,
            errors: SendChannel<ParsingChannelError>
    ) = coroutineScope<Unit> {

        val cachedGroups = mutableListOf<ChannelGroup>()

        val playlist = ParsablePlaylist( playlistContent )
        playlist.extractItems().forEachAsync {
            when( val result = it( playlistPath ) ) {
                is Result.Channel -> {
                    channels.send( result.content )
                    result.content.group { group ->
                        if ( cachedGroups.needsToUpdateWith( group ) )
                            groups.send( group )
                    }
                }
                is Result.Group -> groups.send( result.content )
                is Result.Error -> errors.send( result.error )
                // Explicit else branch needed for avoid to forget smtng, since result is sealed but when is statement
                else -> throw AssertionError("${result::class.qualifiedName} not implemented" )
            }
        }
        channels.close()
        groups.close()
        errors.close()
    }

    /** Extract a [ChannelGroup] from an [IChannel] and execute the lambda [block] with it */
    private suspend fun IChannel.group( block: suspend (ChannelGroup) -> Unit ) {
        val type = when( this ) {
            is MovieChannel -> ChannelGroup.Type.MOVIE
            is TvChannel -> ChannelGroup.Type.MOVIE
            else -> throw AssertionError( "${this::class.qualifiedName} not implemented" )
        }
        block( ChannelGroup( groupName, type ) )
    }

    /**
     * Update a [List] of [ChannelGroup] with the given [ChannelGroup] if needed
     * @return true if the [List] has been updated with the given [ChannelGroup]
     */
    private fun MutableList<ChannelGroup>.needsToUpdateWith( group: ChannelGroup ): Boolean {
        val oldGroup = find { it.id == group.id }
        return when {
            // If group not cached
            oldGroup == null -> {
                add( group )
                true
            }
            // If group cached, but has not valid url, while now has
            oldGroup.imageUrl?.valid != true && group.imageUrl?.valid == true -> {
                this[indexOf( oldGroup )] = group
                true
            }
            else -> false
        }
    }
}

/**
 * An invoke function for execute a [block] within a [PlaylistParser]
 * @return [T]
 */
internal suspend operator fun <T> PlaylistParser.invoke( block: suspend PlaylistParser.() -> T ) = block()