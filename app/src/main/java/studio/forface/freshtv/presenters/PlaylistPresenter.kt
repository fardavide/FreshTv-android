package studio.forface.freshtv.presenters

import studio.forface.freshtv.domain.usecases.GetPlaylist
import studio.forface.freshtv.mappers.SourceFileUiModelMapper
import studio.forface.freshtv.mappers.invoke
import studio.forface.freshtv.uimodels.SourceFileUiModel.Playlist

/**
 * @author Davide Giuseppe Farella
 * A Presenter for get a `Playlist`
 */
internal class PlaylistPresenter(
        private val getPlaylist: GetPlaylist,
        private val mapper: SourceFileUiModelMapper
) {

    /** @return [Playlist] with the given [playlistPath] */
    operator fun invoke( playlistPath: String ) =
            mapper { getPlaylist( playlistPath ).toUiModel() }
}