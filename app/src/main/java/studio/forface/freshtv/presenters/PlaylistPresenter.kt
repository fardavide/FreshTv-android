package studio.forface.freshtv.presenters

import studio.forface.freshtv.uimodels.SourceFileUiModel.Playlist
import studio.forface.freshtv.domain.usecases.GetPlaylist
import studio.forface.freshtv.mappers.SourceFileUiModelMapper

/**
 * @author Davide Giuseppe Farella
 * A Presenter for check get a Playlist
 */
internal class PlaylistPresenter(
        private val getPlaylist: GetPlaylist,
        private val mapper: SourceFileUiModelMapper
) {

    /** @return [Playlist] */
    operator fun invoke( playlistPath: String ) =
            mapper { getPlaylist( playlistPath ).toUiModel() }
}