package studio.forface.freshtv.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.synthetic.main.item_channel_movie.view.*
import studio.forface.freshtv.R.layout.item_channel_movie
import studio.forface.freshtv.commonandroid.adapter.ClickableAdapter
import studio.forface.freshtv.commonandroid.utils.colorOnSurface
import studio.forface.freshtv.commonandroid.utils.inflate
import studio.forface.freshtv.uimodels.MovieChannelUiModel
import studio.forface.theia.dsl.imageDrawable
import studio.forface.theia.dsl.imageUrl
import studio.forface.theia.dsl.invoke
import studio.forface.theia.dsl.placeholderDrawableRes

/**
 * A [PagedListAdapter] for [MovieChannelUiModel]
 * Inherit from [AbsChannelsAdapter]
 *
 * @author Davide Giuseppe Farella.
 */
internal class MovieChannelsAdapter:
        AbsChannelsAdapter<MovieChannelUiModel, MovieChannelsAdapter.MovieChannelViewHolder>( DiffCallback ) {

    /** @see PagedListAdapter.onCreateViewHolder */
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): MovieChannelViewHolder {
        return MovieChannelViewHolder( parent.inflate( item_channel_movie ) )
    }

    /** A [DiffUtil.ItemCallback] for [MovieChannelsAdapter] */
    private object DiffCallback : DiffUtil.ItemCallback<MovieChannelUiModel>() {

        /** @see DiffUtil.ItemCallback.areItemsTheSame */
        override fun areItemsTheSame( old: MovieChannelUiModel, new: MovieChannelUiModel ) =
                old.id == new.id

        /** @see DiffUtil.ItemCallback.areContentsTheSame */
        override fun areContentsTheSame( old: MovieChannelUiModel, new: MovieChannelUiModel ) =
                old == new
    }

    /**
     * A `ViewHolder` for [MovieChannelsAdapter]
     * Inherit from [AbsChannelsAdapter.ViewHolder]
     */
    class MovieChannelViewHolder( itemView: View): AbsChannelsAdapter.ViewHolder<MovieChannelUiModel>( itemView ) {

        /** @see ClickableAdapter.ViewHolder.onBind */
        override fun onBind( item: MovieChannelUiModel ) = with( itemView ) {
            super.onBind( item )

            // Image
            theia {
                item.image?.let { imageUrl = it }
                placeholderDrawableRes = item.imagePlaceHolder
                target = movieChannelImage
            }

            // Name
            movieChannelName.text = item.name

            // Description
            movieChannelDescription.text = item.description

            // Favorite
            movieChannelFavorite.setOnClickListener {
                itemFavoriteChangeInvoker( item.id to ! item.favorite )
            }
            val favoriteDrawable = getDrawable( item.favoriteImage ).apply {
                if ( item.favoriteImageNeedTint ) colorOnSurface( context )
            }
            theia {
                imageDrawable = favoriteDrawable
                target = movieChannelFavorite
            }
        }
    }
}