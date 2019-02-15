package studio.forface.freshtv.interactors

import studio.forface.freshtv.domain.usecases.AddPlaylist
import studio.forface.freshtv.domain.usecases.UpdatePlaylist

/**
 * @author Davide Giuseppe Farella
 * An Interactor for Add or Update a `Playlist`
 */
internal class EditPlaylistInteractor(
        private val addPlaylist: AddPlaylist,
        private val updatePlaylist: UpdatePlaylist
)