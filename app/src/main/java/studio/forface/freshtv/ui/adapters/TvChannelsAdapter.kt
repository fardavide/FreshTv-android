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
import studio.forface.freshtv.commonandroid.utils.colorOnSurface
import studio.forface.freshtv.commonandroid.utils.inflate
import studio.forface.freshtv.domain.usecases.FavoritedChannel
import studio.forface.freshtv.uimodels.TvChannelUiModel
import studio.forface.theia.dsl.imageDrawable
import studio.forface.theia.dsl.imageUrl
import studio.forface.theia.dsl.invoke
import studio.forface.theia.dsl.placeholderDrawableRes

/**
 * A [PagedListAdapter] for [TvChannelUiModel]
 * Inherit from [AbsChannelsAdapter]
 *
 * @author Davide Giuseppe Farella.
 */
internal class TvChannelsAdapter:
    AbsChannelsAdapter<TvChannelUiModel, TvChannelsAdapter.TvChannelViewHolder>( DiffCallback ) {

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
     * Inherit from [AbsChannelsAdapter.ViewHolder]
     */
    class TvChannelViewHolder( itemView: View ): AbsChannelsAdapter.ViewHolder<TvChannelUiModel>( itemView ) {

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
            val favoriteDrawable = getDrawable( item.favoriteImage ).apply {
                if ( item.favoriteImageNeedTint ) colorOnSurface( context )
            }
            theia {
                imageDrawable = favoriteDrawable
                target = tvChannelFavorite
            }

            // Program
            tvChannelProgram.isVisible = item.currentProgram != null
            item.currentProgram?.let { program ->
                tvChannelProgramName.text = program.title
                tvChannelProgramStartTime.text = program.startTime
                tvChannelProgramEndTime.text = program.endTime
                tvChannelProgramProgress.progress = program.progressPercentage
            }
        }
    }
}