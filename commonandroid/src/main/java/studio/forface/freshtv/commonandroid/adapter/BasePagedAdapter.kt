package studio.forface.freshtv.commonandroid.adapter

import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import studio.forface.freshtv.commonandroid.adapter.ClickableAdapter.ViewHolder

/**
 * @author Davide Giuseppe Farella.
 * A [PagedListAdapter] that contains a [List] of [UiModel] items.
 *
 * @param itemCallback a REQUIRED [DiffUtil.ItemCallback] of [UiModel] that will be used
 * for compare the items.
 *
 * Inherit from [PagedListAdapter].
 */
abstract class BasePagedAdapter<UiModel, ViewHolder: ClickableAdapter.ViewHolder<UiModel>>(
        itemCallback: DiffUtil.ItemCallback<UiModel>
): PagedListAdapter<UiModel, ViewHolder>( itemCallback ), ClickableAdapter<UiModel> {

    /** A callback that will be triggered when an item is clicked */
    override var onItemClick: (UiModel) -> Unit = {}

    /** A callback that will be triggered when an item is long clicked */
    override var onItemLongClick: (UiModel) -> Unit = {}

    /** @see PagedListAdapter.onBindViewHolder */
    override fun onBindViewHolder( holder: ViewHolder, position: Int ) {
        getItem( position )?.let {
            holder.onBind( it )
            holder.prepareClickListeners()
        } // TODO ?: holder.clear()
    }
}