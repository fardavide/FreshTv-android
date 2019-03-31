package studio.forface.freshtv.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.synthetic.main.item_channel_movie.view.*
import studio.forface.freshtv.R.layout.item_channel_movie
import studio.forface.freshtv.commonandroid.adapter.BasePagedAdapter
import studio.forface.freshtv.commonandroid.adapter.ClickableAdapter
import studio.forface.freshtv.commonandroid.utils.inflate
import studio.forface.freshtv.domain.usecases.FavoritedChannel
import studio.forface.freshtv.uimodels.MovieChannelUiModel
import studio.forface.theia.dsl.imageDrawableRes
import studio.forface.theia.dsl.imageUrl
import studio.forface.theia.dsl.invoke
import studio.forface.theia.dsl.placeholderDrawableRes

/**
 * @author Davide Giuseppe Farella.
 * A [PagedListAdapter] for [MovieChannelUiModel]
 *
 * Inherit from [ClickableAdapter]
 */
internal class MovieChannelsAdapter:
        BasePagedAdapter<MovieChannelUiModel, MovieChannelsAdapter.MovieChannelViewHolder>( DiffCallback ) {

    /** A callback that will be triggered when an item is long clicked */
    var onItemFavoriteChange: (FavoritedChannel) -> Unit = {}

    /**
     * An invoker for [onItemLongClick], we use it so the `ViewHolder` will always use the updated
     * [onItemLongClick] even if it changes after the `ViewHolder` is created.
     */
    val itemFavoriteChangeInvoker: (FavoritedChannel) -> Unit get() = { onItemFavoriteChange( it ) }

    /** @see PagedListAdapter.onCreateViewHolder */
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): MovieChannelViewHolder {
        return MovieChannelViewHolder( parent.inflate( item_channel_movie ) )
    }

    override fun prepareClickListeners( holder: MovieChannelsAdapter.MovieChannelViewHolder ) {
        super.prepareClickListeners( holder )
        holder.itemFavoriteChangeInvoker = this.itemFavoriteChangeInvoker
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
     * Inherit from [ClickableAdapter.ViewHolder]
     */
    class MovieChannelViewHolder( itemView: View): ClickableAdapter.ViewHolder<MovieChannelUiModel>( itemView ) {

        internal var itemFavoriteChangeInvoker: (FavoritedChannel) -> Unit = {}

        /** @see ClickableAdapter.ViewHolder.onBind */
        override fun onBind( item: MovieChannelUiModel ) = with<View, Unit>( itemView ) {
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
            theia {
                imageDrawableRes = item.favoriteImage
                target = movieChannelFavorite
            }
        }
    }
}