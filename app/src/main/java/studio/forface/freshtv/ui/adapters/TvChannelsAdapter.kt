package studio.forface.freshtv.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.synthetic.main.item_channel_tv.view.*
import studio.forface.freshtv.R.layout.item_channel_tv
import studio.forface.freshtv.commonandroid.adapter.BasePagedAdapter
import studio.forface.freshtv.commonandroid.adapter.ClickableAdapter
import studio.forface.freshtv.commonandroid.utils.inflate
import studio.forface.freshtv.domain.usecases.FavoritedChannel
import studio.forface.freshtv.uimodels.TvChannelUiModel
import studio.forface.theia.dsl.imageDrawableRes
import studio.forface.theia.dsl.imageUrl
import studio.forface.theia.dsl.invoke
import studio.forface.theia.dsl.placeholderDrawableRes

/**
 * @author Davide Giuseppe Farella.
 * A [PagedListAdapter] for [TvChannelUiModel]
 *
 * Inherit from [ClickableAdapter]
 */
internal class TvChannelsAdapter:
        BasePagedAdapter<TvChannelUiModel, TvChannelsAdapter.TvChannelViewHolder>( DiffCallback ) {

    /** A callback that will be triggered when an item is long clicked */
    var onItemFavoriteChange: (FavoritedChannel) -> Unit = {}

    /**
     * An invoker for [onItemLongClick], we use it so the `ViewHolder` will always use the updated
     * [onItemLongClick] even if it changes after the `ViewHolder` is created.
     */
    val itemFavoriteChangeInvoker: (FavoritedChannel) -> Unit get() = { onItemFavoriteChange( it ) }

    /** @see PagedListAdapter.onCreateViewHolder */
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): TvChannelViewHolder {
        return TvChannelViewHolder( parent.inflate( item_channel_tv ) )
    }

    override fun prepareClickListeners( holder: TvChannelsAdapter.TvChannelViewHolder ) {
        super.prepareClickListeners( holder )
        holder.itemFavoriteChangeInvoker = this.itemFavoriteChangeInvoker
    }

    /** A [DiffUtil.ItemCallback] for [TvChannelsAdapter] */
    private object DiffCallback : DiffUtil.ItemCallback<TvChannelUiModel>() {

        /** @see DiffUtil.ItemCallback.areItemsTheSame */
        override fun areItemsTheSame( old: TvChannelUiModel, new: TvChannelUiModel ) =
                old.id == new.id

        /** @see DiffUtil.ItemCallback.areContentsTheSame */
        override fun areContentsTheSame( old: TvChannelUiModel, new: TvChannelUiModel ) =
                old == new
    }

    /**
     * A `ViewHolder` for [TvChannelsAdapter]
     * Inherit from [ClickableAdapter.ViewHolder]
     */
    class TvChannelViewHolder( itemView: View): ClickableAdapter.ViewHolder<TvChannelUiModel>( itemView ) {

        internal var itemFavoriteChangeInvoker: (FavoritedChannel) -> Unit = {}

        /** @see ClickableAdapter.ViewHolder.onBind */
        override fun onBind( item: TvChannelUiModel ) = with<View, Unit>( itemView ) {
            super.onBind( item )

            // Image
            theia {
                item.image?.let { imageUrl = it }
                placeholderDrawableRes = item.imagePlaceHolder
                target = tvChannelImage
            }

            // Name
            tvChannelName.text = item.name

            // Favorite
            tvChannelFavorite.setOnClickListener {
                itemFavoriteChangeInvoker( item.id to ! item.favorite )
            }
            theia {
                imageDrawableRes = item.favoriteImage
                target = tvChannelFavorite
            }

            // Program
            tvChannelProgram.isVisible = item.currentProgram != null
            item.currentProgram?.let { program ->
                tvChannelProgramStartTime.text = program.startTime
                tvChannelProgramEndTime.text = program.endTime
                tvChannelProgramProgress.progress = program.progressPercentage
            }
        }
    }
}