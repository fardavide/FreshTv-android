package studio.forface.freshtv.playlistsource

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test
import studio.forface.freshtv.domain.entities.Playlist
import studio.forface.freshtv.domain.gateways.invoke
import studio.forface.freshtv.playlistsource.parser.PlaylistParser

/**
 * @author Davide Giuseppe Farella
 * Test class for [PlaylistSourceImpl]
 */
internal class PlaylistSourceImplTest {

    private val mockLocal = mockk<PlaylistContentResolver.Local> {
        coEvery { this@mockk(any()) } answers { mockPlaylistContent }
    }

    private val mockRemote = mockk<PlaylistContentResolver.Remote> {
        coEvery { this@mockk(any()) } answers { mockPlaylistContent }
    }

    private val source = PlaylistSourceImpl(
            PlaylistContentResolver( mockLocal, mockRemote ),
            PlaylistParser()
    )

    @Test
    fun `readFrom`() {
        var channelCalled = false
        var groupCalled = false
        var errorCalled = false

        runBlocking {
            source { readFrom(
                    Playlist("", Playlist.Type.LOCAL ),
                    { channelCalled = true },
                    { groupCalled = true },
                    { errorCalled = true }
            ) }
        }

        coVerify( exactly = 1 ) { mockLocal.invoke( any() ) }
        coVerify( exactly = 0 ) { mockRemote.invoke( any() ) }
        assertTrue( channelCalled )
        assertTrue( groupCalled )
        assertTrue( errorCalled )
    }

    @Test
    fun `readFrom remotePlaylist fromRemoteSource`() {
        runBlocking {
            source { readFrom(
                    Playlist("", Playlist.Type.REMOTE ),
                    {}, {}, {}
            ) }
        }

        coVerify( exactly = 0 ) { mockLocal.invoke( any() ) }
        coVerify( exactly = 1 ) { mockRemote.invoke( any() ) }
    }
}