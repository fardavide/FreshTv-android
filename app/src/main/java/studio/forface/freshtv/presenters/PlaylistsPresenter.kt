package studio.forface.freshtv.presenters

import androidx.paging.DataSource
import studio.forface.freshtv.androiddatabase.usecases.GetPagedPlaylists
import studio.forface.freshtv.mappers.SourceFileUiModelMapper
import studio.forface.freshtv.commonandroid.mappers.map
import studio.forface.freshtv.uimodels.SourceFileUiModel

/**
 * @author Davide Giuseppe Farella
 * A Presenter for get all the stored `Playlist`s
 */
internal class PlaylistsPresenter(
    private val getPlaylists: GetPagedPlaylists,
    private val mapper: SourceFileUiModelMapper
) {

    /** @return a [DataSource.Factory] of [SourceFileUiModel] */
    operator fun invoke() = getPlaylists().map( mapper ) { it.toUiModel() }
}