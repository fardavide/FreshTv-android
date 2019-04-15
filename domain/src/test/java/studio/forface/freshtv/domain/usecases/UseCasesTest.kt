@file:Suppress("FunctionName")

package studio.forface.freshtv.domain.usecases

import io.mockk.*
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.runBlocking
import studio.forface.freshtv.domain.entities.MovieChannel
import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.domain.entities.TvChannel
import studio.forface.freshtv.domain.entities.TvGuide
import studio.forface.freshtv.domain.gateways.Parsers
import studio.forface.freshtv.domain.mockLocalData
import studio.forface.freshtv.domain.mockPlaylist
import studio.forface.freshtv.domain.mockTvGuide
import studio.forface.freshtv.domain.realEpgSource
import java.lang.Math.random
import kotlin.random.Random
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * @author Davide Giuseppe Farella
 * Test class for Use Case's
 */
class UseCasesTest {

    @BeforeTest
    fun reset() {
        mockLocalData.deleteAllChannels()
    }

    @Test
    fun addPlaylist() {
        val playlists = mockPlaylist
        val add = AddPlaylist( mockLocalData )
        add( playlists.path, playlists.type, playlists.name!! )

        verify { mockLocalData.storePlaylist( playlists ) }
        assertEquals( mockLocalData.playlists(), listOf( playlists ) )
    }

    @Test
    fun getTvGuides() {
        val guides = ( 1..10 ).map { mockTvGuide.copy( id = it.toString(), channelId = "Rai Uno" ) }
        mockLocalData.storeTvGuides( guides )

        val get = GetTvGuides( mockLocalData )
        assertEquals(10, get("Rai Uno" ).size )
    }

    @Test
    fun incrementChannelMediaFailure() {
        val tv = TvChannel("tv","tvName", mediaUrls = mutableMapOf( "url" to 5 ) )
        mockLocalData.storeChannel( tv )
        assertEquals( mockLocalData.tvChannels().first().mediaUrls["url"],5 )

        IncrementChannelMediaFailure( mockLocalData )( tv,"url" )
        assertEquals( mockLocalData.tvChannels().first().mediaUrls["url"],6 )
    }

    @Test
    fun `incrementChannelMediaFailure byId`() {
        val tv = TvChannel("tv","tvName", mediaUrls = mutableMapOf( "url" to 5 ) )
        mockLocalData.storeChannel( tv )
        assertEquals( mockLocalData.tvChannels().first().mediaUrls["url"],5 )

        IncrementChannelMediaFailure( mockLocalData )("tv","url" )
        assertEquals( mockLocalData.tvChannels().first().mediaUrls["url"],6 )
    }

    @Test
    fun refreshTvGuides() {
        val count = Random.nextInt(50 )

        val mockParsers = mockk<Parsers> {
            coEvery { readFrom( epg = any(), onTvGuide = any(), onError = any(), onProgress = any() ) } coAnswers {
                with( secondArg<suspend (TvGuide) -> Unit>() ) {
                    repeat( count ) { this( mockTvGuide.copy( id = it.toString() ) ) }
                }
            }
        }
        runBlocking { RefreshTvGuides( mockLocalData, mockParsers )( realEpgSource ) }

        assertEquals( count, mockLocalData.countTvGuides() )
    }

    @Test
    fun removeChannelMediaUrl() {
        val tv = TvChannel("tv","tvName", mediaUrls = mutableMapOf( "url" to 5 ) )
        mockLocalData.storeChannel( tv )
        assertEquals( mockLocalData.tvChannels().first().mediaUrls.size,1 )

        RemoveChannelMediaUrl( mockLocalData )( tv,"url" )
        assertEquals( mockLocalData.tvChannels().first().mediaUrls.size,0 )
    }

    @Test
    fun `removeChannelMediaUrl byId`() {
        val tv = TvChannel("tv","tvName", mediaUrls = mutableMapOf( "url" to 5 ) )
        mockLocalData.storeChannel( tv )
        assertEquals( mockLocalData.tvChannels().first().mediaUrls.size,1 )

        RemoveChannelMediaUrl( mockLocalData )( "tv","url" )
        assertEquals( mockLocalData.tvChannels().first().mediaUrls.size,0 )
    }

    @Test
    fun renameChannel() {
        val id = "id"
        val oldMovie = MovieChannel( id, "oldName" )
        mockLocalData.storeChannel( oldMovie )
        assertEquals( mockLocalData.channel( id ).name, "oldName" )

        RenameChannel( mockLocalData )( oldMovie, "newName" )
        assertEquals( mockLocalData.channel( id ).name, "newName" )
    }

    @Test
    fun `renameChannel byId`() {
        val id = "id"
        val oldMovie = MovieChannel( id, "oldName" )
        mockLocalData.storeChannel( oldMovie )
        assertEquals( mockLocalData.channel( id ).name, "oldName" )

        RenameChannel( mockLocalData )( "id", "newName" )
        assertEquals( mockLocalData.channel( id ).name, "newName" )
    }

    @Test
    fun resetChannelMediaFailure() {
        val tv = TvChannel("tv","tvName", mediaUrls = mutableMapOf( "url" to 5 ) )
        mockLocalData.storeChannel( tv )
        assertEquals( mockLocalData.tvChannels().first().mediaUrls["url"],5 )

        ResetChannelMediaFailure( mockLocalData )( tv,"url" )
        assertEquals( mockLocalData.tvChannels().first().mediaUrls["url"],0 )
    }

    @Test
    fun `resetChannelMediaFailure byId`() {
        val tv = TvChannel("tv","tvName", mediaUrls = mutableMapOf( "url" to 5 ) )
        mockLocalData.storeChannel( tv )
        assertEquals( mockLocalData.tvChannels().first().mediaUrls["url"],5 )

        ResetChannelMediaFailure( mockLocalData )( "tv","url" )
        assertEquals( mockLocalData.tvChannels().first().mediaUrls["url"],0 )
    }
}