package studio.forface.freshtv.ui.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import studio.forface.freshtv.commonandroid.adapter.ClickableAdapter
import studio.forface.freshtv.commonandroid.utils.inflate
import studio.forface.freshtv.uimodels.TvChannelUiModel

/**
 * @author Davide Giuseppe Farella.
 * A [PagedListAdapter] for [TvChannelUiModel]
 *
 * Implements [ClickableAdapter]
 */
internal class TvChannelsAdapter:
        PagedListAdapter<TvChannelUiModel, TvChannelsAdapter.TvChannelViewHolder>( DiffCallback ),
        ClickableAdapter<TvChannelUiModel> {

    /** A callback that will be triggered when an item is clicked */
    override var onItemClick: (TvChannelUiModel) -> Unit = {}

    /** A callback that will be triggered when an item is long clicked */
    override var onItemLongClick: (TvChannelUiModel) -> Unit = {}

    /** @see PagedListAdapter.onCreateViewHolder */
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): TvChannelViewHolder {
        return TvChannelViewHolder( TextView( parent.context ) ) // TODO inflate real view
    }

    /** @see PagedListAdapter.onBindViewHolder */
    override fun onBindViewHolder( holder: TvChannelViewHolder, position: Int ) {
        getItem( position )?.let {
            holder.onBind( it )
            holder.prepareClickListeners()
        } // TODO ?: holder.clear()
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
        override fun onBind( item: TvChannelUiModel ) {
            super.onBind( item )
            ( itemView as TextView ).text = item.name
        }
    }
}