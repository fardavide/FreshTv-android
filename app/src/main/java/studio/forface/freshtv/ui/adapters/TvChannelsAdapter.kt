package studio.forface.freshtv.ui.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.synthetic.main.item_channel_tv.view.*
import studio.forface.freshtv.R.layout.item_channel_tv
import studio.forface.freshtv.commonandroid.adapter.BasePagedAdapter
import studio.forface.freshtv.commonandroid.adapter.ClickableAdapter
import studio.forface.freshtv.commonandroid.imageloader.invoke
import studio.forface.freshtv.commonandroid.utils.inflate
import studio.forface.freshtv.uimodels.TvChannelUiModel

/**
 * @author Davide Giuseppe Farella.
 * A [PagedListAdapter] for [TvChannelUiModel]
 *
 * Inherit from [ClickableAdapter]
 */
internal class TvChannelsAdapter:
        BasePagedAdapter<TvChannelUiModel, TvChannelsAdapter.TvChannelViewHolder>( DiffCallback ) {

    /** @see PagedListAdapter.onCreateViewHolder */
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): TvChannelViewHolder {
        return TvChannelViewHolder( parent.inflate( item_channel_tv ) )
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

        /** @see ClickableAdapter.ViewHolder.onBind */
        override fun onBind( item: TvChannelUiModel ) = with( itemView ) {
            super.onBind( item )

            // Image
            imageLoader {
                image = item.image
                target = tvChannelImage
            }

            // Name
            tvChannelName.text = item.name

            // Favorite
            tvChannelFavorite.setOnClickListener { /* TODO */ }
            imageLoader {
                image = item.favoriteImage
                target = tvChannelFavorite
            }

            // Program
            item.currentProgram?.let { program ->

            }
        }
    }
}