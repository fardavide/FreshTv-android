package studio.forface.freshtv.presenters

import studio.forface.freshtv.commonandroid.mappers.invoke
import studio.forface.freshtv.domain.usecases.GetEpg
import studio.forface.freshtv.domain.usecases.GetPlaylist
import studio.forface.freshtv.mappers.SourceFileUiModelMapper
import studio.forface.freshtv.uimodels.SourceFileUiModel
import studio.forface.freshtv.uimodels.SourceFileUiModel.Epg
import studio.forface.freshtv.uimodels.SourceFileUiModel.Playlist

/**
 * @author Davide Giuseppe Farella
 *
 * A Presenter for get a Source File
 */
internal abstract class SourceFilePresenter {

    /** @return [SourceFileUiModel] with the given [filePath] */
    abstract operator fun invoke( filePath: String ): SourceFileUiModel
}

/**
 * A Presenter for get an `EPG`
 * Inherit from [SourceFilePresenter]
 *
 * @author Davide Giuseppe Farella
 */
internal class EpgPresenter(
    private val getEpg: GetEpg,
    private val mapper: SourceFileUiModelMapper
) : SourceFilePresenter() {

    /** @return [Epg] with the given [filePath] */
    override operator fun invoke( filePath: String ) =
        mapper { getEpg( filePath ).toUiModel() }
}

/**
 * A Presenter for get a `Playlist`
 * Inherit from [SourceFilePresenter]
 *
 * @author Davide Giuseppe Farella
 */
internal class PlaylistPresenter(
    private val getPlaylist: GetPlaylist,
    private val mapper: SourceFileUiModelMapper
) : SourceFilePresenter() {

    /** @return [Playlist] with the given [filePath] */
    override operator fun invoke( filePath: String ) =
        mapper { getPlaylist( filePath ).toUiModel() }
}