package studio.forface.freshtv.playlistsource

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test
import studio.forface.freshtv.domain.entities.SourceFile
import studio.forface.freshtv.domain.entities.SourceFile.Playlist
import studio.forface.freshtv.playlistsource.playlist.PlaylistParser

/**
 * @author Davide Giuseppe Farella
 * Test class for [ParsersImpl]
 */
internal class ParsersImplTest {

    private val mockLocal = mockk<FileContentResolver.Local> {
        coEvery { this@mockk(any()) } answers { mockPlaylistContent }
    }

    private val mockRemote = mockk<FileContentResolver.Remote> {
        coEvery { this@mockk(any()) } answers { mockPlaylistContent }
    }

    private val source = ParsersImpl(
            FileContentResolver( mockLocal, mockRemote ),
            PlaylistParser()
    )

    @Test
    fun `readFrom executeCorrectly`() {
        var channelCalled = false
        var groupCalled = false
        var errorCalled = false

        runBlocking {
            source.readFrom(
                    Playlist("", SourceFile.Type.LOCAL ),
                    { channelCalled = true },
                    { groupCalled = true },
                    { errorCalled = true }
            )
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
            source.readFrom(
                Playlist("", SourceFile.Type.REMOTE ),
                {}, {}, {}
            )
        }

        coVerify( exactly = 0 ) { mockLocal.invoke( any() ) }
        coVerify( exactly = 1 ) { mockRemote.invoke( any() ) }
    }

    // @Test // TODO test only manually due to http call
    fun realTest() {
        val source = ParsersImpl()

        runBlocking {
            source.readFrom(
                Playlist("https://sourcetv.info/dl/01/it29.m3u", SourceFile.Type.REMOTE ),
                { println( it ) },
                { println( it ) },
                { println( "${it.reason.name} - ${it.rawChannel}" ) }
            )
        }
    }
}