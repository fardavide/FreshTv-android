package studio.forface.freshtv.presenters

import androidx.paging.DataSource
import studio.forface.freshtv.androiddatabase.usecases.GetPagedMovieChannels
import studio.forface.freshtv.commonandroid.mappers.map
import studio.forface.freshtv.mappers.MovieChannelUiModelMapper
import studio.forface.freshtv.uimodels.MovieChannelUiModel

/**
 * A Presenter for get `MovieChannel`s
 *
 * @author Davide Giuseppe Farella
 */
internal class MovieChannelsPresenter(
        private val getPagedMovieChannels: GetPagedMovieChannels,
        private val mapper: MovieChannelUiModelMapper
) {

    /**
     * @return a [DataSource] of [MovieChannelUiModel]
     * @param groupName OPTIONAL [String] for filter results by `Channel`s group name.
     */
    operator fun invoke( groupName: String ) = getPagedMovieChannels( groupName )
            .map( mapper ) { it.toUiModel() }
}