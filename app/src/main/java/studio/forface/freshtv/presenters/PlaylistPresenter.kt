package studio.forface.freshtv.presenters

import studio.forface.freshtv.domain.usecases.GetPlaylist
import studio.forface.freshtv.mappers.SourceFileUiModelMapper
import studio.forface.freshtv.mappers.invoke
import studio.forface.freshtv.uimodels.SourceFileUiModel.Playlist

/**
 * @author Davide Giuseppe Farella
 * A Presenter for get a `Playlist`
 *
 * Inherit from [AbsSourceFilePresenter]
 */
internal class PlaylistPresenter(
        private val getPlaylist: GetPlaylist,
        override val mapper: SourceFileUiModelMapper
) : AbsSourceFilePresenter() {

    /** @return [Playlist] with the given [filePath] */
    override operator fun invoke( filePath: String ) =
            mapper { getPlaylist( filePath ).toUiModel() }
}