package studio.forface.freshtv.ui.adapters

import android.view.View
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import studio.forface.freshtv.commonandroid.adapter.BasePagedAdapter
import studio.forface.freshtv.commonandroid.adapter.ClickableAdapter
import studio.forface.freshtv.domain.usecases.FavoritedChannel
import studio.forface.freshtv.domain.utils.handle
import studio.forface.freshtv.uimodels.ChannelUiModel
import studio.forface.freshtv.uimodels.MovieChannelUiModel

/**
 * A [PagedListAdapter] for [MovieChannelUiModel]
 * Inherit from [ClickableAdapter]
 *
 * @author Davide Giuseppe Farella
 */
internal abstract class AbsChannelsAdapter<
        UiModel: ChannelUiModel,
        ViewHolder: AbsChannelsAdapter.ViewHolder<UiModel>
>(
    diffCallback: DiffUtil.ItemCallback<UiModel>
) : BasePagedAdapter<UiModel, ViewHolder>( diffCallback ) {

    /** A callback that will be triggered when an item is long clicked */
    var onItemFavoriteChange: (FavoritedChannel) -> Unit = {}

    /**
     * An invoker for [onItemLongClick], we use it so the `ViewHolder` will always use the updated
     * [onItemLongClick] even if it changes after the `ViewHolder` is created.
     */
    private val itemFavoriteChangeInvoker: (FavoritedChannel) -> Unit get() = { onItemFavoriteChange( it ) }

    /** @see ClickableAdapter.prepareClickListeners */
    override fun prepareClickListeners( holder: ViewHolder) {
        super.prepareClickListeners( holder )
        holder.itemFavoriteChangeInvoker = this.itemFavoriteChangeInvoker
    }

    /** @return OPTIONAL [String] representing the [ChannelUiModel.id] for item at the given [position] */
    fun getChannelId( position: Int ) = handle { getItem( position )?.id }

    /**
     * An abstract `ViewHolder` for [AbsChannelsAdapter]
     * Inherit from [ClickableAdapter.ViewHolder]
     */
    abstract class ViewHolder<UiModel: ChannelUiModel>(
        itemView: View
    ) : ClickableAdapter.ViewHolder<UiModel>( itemView ) {

        internal var itemFavoriteChangeInvoker: (FavoritedChannel) -> Unit = {}
    }
}