@file:Suppress("EXPERIMENTAL_API_USAGE")

package studio.forface.freshtv.parsers.playlist

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import studio.forface.freshtv.domain.entities.ChannelGroup
import studio.forface.freshtv.domain.entities.IChannel
import studio.forface.freshtv.domain.entities.MovieChannel
import studio.forface.freshtv.domain.entities.TvChannel
import studio.forface.freshtv.domain.errors.ParsingChannelError
import studio.forface.freshtv.domain.utils.forEachAsync
import studio.forface.freshtv.domain.utils.wait
import studio.forface.freshtv.parsers.playlist.ParsablePlaylistItem.Result
import java.io.InputStream

/**
 * @author Davide Giuseppe Farella.
 * A class that parse a [String] playlist
 */
internal class PlaylistParser {

    /** Parse the [playlistContent] and submit items via the given [SendChannel]s */
    suspend operator fun invoke(
        playlistPath: String,
        playlistContent: String,
        channels: SendChannel<IChannel>,
        groups: SendChannel<ChannelGroup>,
        errors: SendChannel<ParsingChannelError>
    ) = coroutineScope<Unit> {

        val cachedGroups = mutableListOf<ChannelGroup>()

        val playlist = ParsableStringPlaylist( playlistContent )
        playlist.extractItems().forEachAsync {
            when ( val result = it( playlistPath ) ) {
                is Result.Channel -> {
                    channels.send( result.content )
                    result.content.group { group ->
                        if ( cachedGroups.needsToUpdateWith( group ) )
                            groups.send( group )
                    }
                }
                is Result.Group -> groups.send( result.content )
                is Result.Error -> errors.send( result.error )
                // Explicit else branch needed for avoid to forget something, since result is sealed but when is
                // statement
                else -> throw AssertionError("${result::class.qualifiedName} not implemented" )
            }
        }
        channels.close()
        groups.close()
        errors.close()
    }

    /** Parse the [playlistStream] and submit items via the given [SendChannel]s */
    suspend operator fun invoke(
        playlistPath: String,
        playlistStream: InputStream,
        channels: SendChannel<IChannel>,
        groups: SendChannel<ChannelGroup>,
        errors: SendChannel<ParsingChannelError>
    ) = coroutineScope<Unit> {

        val cachedGroups = mutableListOf<ChannelGroup>()

        val items = Channel<ParsablePlaylistItem>()
        val playlist = ParsablePlaylist( playlistStream )
        playlist.extractItems( items )

        for ( item in items ) {
            when ( val result = item( playlistPath ) ) {
                is Result.Channel -> {
                    channels.send( result.content )
                    result.content.group { group ->
                        if ( cachedGroups.needsToUpdateWith( group ) )
                            groups.send( group )
                    }
                }
                is Result.Group -> groups.send( result.content )
                is Result.Error -> errors.send( result.error )
                // Explicit else branch needed for avoid to forget something, since result is sealed but when is
                // statement
                else -> throw AssertionError("${result::class.qualifiedName} not implemented" )
            }
        }

        wait { items.isClosedForReceive }

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