package studio.forface.freshtv.ui.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import studio.forface.freshtv.commonandroid.adapter.BasePagedAdapter
import studio.forface.freshtv.commonandroid.adapter.ClickableAdapter
import studio.forface.freshtv.uimodels.SourceFileUiModel

/**
 * @author Davide Giuseppe Farella.
 * A [PagedListAdapter] for [SourceFileUiModel]
 *
 * Inherit from [BasePagedAdapter]
 */
internal class SourceFilesAdapter:
        BasePagedAdapter<SourceFileUiModel, SourceFilesAdapter.PlaylistViewHolder>( DiffCallback ) {

    /** @see PagedListAdapter.onCreateViewHolder */
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): PlaylistViewHolder {
        return PlaylistViewHolder( TextView( parent.context ) ) // TODO inflate real view
    }

    /** A [DiffUtil.ItemCallback] for [SourceFilesAdapter] */
    private object DiffCallback : DiffUtil.ItemCallback<SourceFileUiModel>() {

        /** @see DiffUtil.ItemCallback.areItemsTheSame */
        override fun areItemsTheSame( old: SourceFileUiModel, new: SourceFileUiModel ) =
                old.fullPath == new.fullPath

        /** @see DiffUtil.ItemCallback.areContentsTheSame */
        override fun areContentsTheSame( old: SourceFileUiModel, new: SourceFileUiModel ) =
                old == new
    }

    /**
     * A `ViewHolder` for [SourceFilesAdapter]
     * Inherit from [ClickableAdapter.ViewHolder]
     */
    class PlaylistViewHolder( itemView: View ): ClickableAdapter.ViewHolder<SourceFileUiModel>( itemView ) {

        /** @see ClickableAdapter.ViewHolder.onBind */
        override fun onBind( item: SourceFileUiModel ) {
            super.onBind( item )
            ( itemView as TextView ).text = item.fullPath
        }
    }
}