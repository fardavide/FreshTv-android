@file:Suppress("FunctionName")

package studio.forface.freshtv.domain.usecases

import io.mockk.verify
import kotlinx.coroutines.runBlocking
import studio.forface.freshtv.domain.entities.MovieChannel
import studio.forface.freshtv.domain.entities.TvChannel
import studio.forface.freshtv.domain.mockLocalData
import studio.forface.freshtv.domain.mockPlaylist
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
        val add = AddPlaylist(mockLocalData)
        add( playlists.path, playlists.type, playlists.name )

        verify { mockLocalData.storePlaylist( playlists ) }
        assertEquals( mockLocalData.playlists(), listOf( playlists ) )
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

        IncrementChannelMediaFailure( mockLocalData )( "tv","url" )
        assertEquals( mockLocalData.tvChannels().first().mediaUrls["url"],6 )
    }

    @Test
    fun refreshPlaylists() {
        TODO("not implemented" )
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