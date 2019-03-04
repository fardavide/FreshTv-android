package studio.forface.freshtv.commonandroid.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import studio.forface.freshtv.commonandroid.adapter.ClickableAdapter.ViewHolder

/**
 * @author Davide Giuseppe Farella.
 * A [RecyclerView.Adapter] that contains a [List] of [T] items.
 *
 * @param itemsComparator a REQUIRED [ItemsComparator] of [T] that will be used from
 * [DiffCallback] for compare the items.
 *
 * Inherit from [RecyclerView.Adapter].
 */
abstract class BaseAdapter<T, ViewHolder: ClickableAdapter.ViewHolder<T>>(
        private val itemsComparator: ItemsComparator<T>
): RecyclerView.Adapter<ViewHolder>(), ClickableAdapter<T, ViewHolder> {

    /**
     * A [List] of items [T].
     * We overridden the `set` method for dispatch updates through a [DiffCallback].
     */
    var items = listOf<T>()
        set( value ) {
            val oldValue = field
            field = value

            val diffResult = DiffUtil.calculateDiff( DiffCallback( oldValue, value, itemsComparator ) )
            diffResult.dispatchUpdatesTo(this )
        }

    /** A callback that will be triggered when an item is clicked */
    override var onItemClick: (T) -> Unit = {}

    /** A callback that will be triggered when an item is long clicked */
    override var onItemLongClick: (T) -> Unit = {}

    /**
     * GET the size of all the Items in the Adapter.
     * @see items
     */
    override fun getItemCount(): Int = items.size

    /**
     * Get the Item for the requested [position], call [ViewHolder.onBind] and set
     * [clickListenerInvoker] and [longClickListenerInvoker] for the [holder].
     * @see RecyclerView.Adapter.onBindViewHolder
     */
    override fun onBindViewHolder( holder: ViewHolder, position: Int ) {
        val item = items[position]
        holder.onBind( item )
        prepareClickListeners( holder )
    }

    /**
     * A [DiffUtil.Callback] for [BaseAdapter].
     *
     * @param itemsComparator an [ItemsComparator] of [T] for compare old items to new items.
     */
    class DiffCallback<T>(
            private val oldList: List<T>, private val newList: List<T>,
            private val itemsComparator: ItemsComparator<T>
    ) : DiffUtil.Callback() {

        /**
         * @see DiffUtil.Callback.getOldListSize
         */
        override fun getOldListSize() = oldList.size

        /**
         * @see DiffUtil.Callback.getNewListSize
         */
        override fun getNewListSize(): Int = newList.size

        /**
         * @see DiffUtil.Callback.areItemsTheSame
         */
        override fun areItemsTheSame( oldItemPosition: Int, newItemPosition: Int ): Boolean =
                itemsComparator.areItemsTheSame( oldList[oldItemPosition], newList[newItemPosition] )

        /**
         * @see DiffUtil.Callback.areContentsTheSame
         */
        override fun areContentsTheSame( oldItemPosition: Int, newItemPosition: Int ): Boolean =
                itemsComparator.areContentsTheSame( oldList[oldItemPosition], newList[newItemPosition] )

        /**
         * @see DiffUtil.Callback.getChangePayload
         */
        override fun getChangePayload( oldItemPosition: Int, newItemPosition: Int ): Any? =
                itemsComparator.getChangePayload( oldList[oldItemPosition], newList[newItemPosition] )
    }

    /**
     * An abstract class for compare two items [T] for declare if they are the same items and
     * if the have the same contents.
     *
     * Used by [DiffCallback].
     */
    abstract class ItemsComparator<T> {
        /**
         * Called by the [DiffCallback.areItemsTheSame] to decide whether two object represent
         * the same Item.
         * <p>
         * For example, if your items have unique ids, this method should check their id equality.
         *
         * @return True if the two items represent the same object or false if they are different.
         */
        abstract fun areItemsTheSame( oldItem: T, newItem: T ): Boolean

        /**
         * Called by the [DiffCallback.areContentsTheSame] when it wants to check whether two
         * items have the same data.
         * [DiffUtil] uses this information to detect if the contents of an item has changed.
         * <p>
         * DiffUtil uses this method to check equality instead of [Object.equals] so that you
         * can change its behavior depending on your UI.
         * For example, if you are using DiffUtil with a [RecyclerView.Adapter], you should
         * return whether the items' visual representations are the same.
         * <p>
         * This method is called only if [areItemsTheSame] returns `true` for these items.
         *
         * @return True if the contents of the items are the same or false if they are different.
         */
        open fun areContentsTheSame( oldItem: T, newItem: T ): Boolean =
                oldItem == newItem

        /**
         * When [areItemsTheSame] returns `true` for two items and [areContentsTheSame] returns
         * false for them, DiffUtil
         * calls this method to get a payload about the change.
         *
         *
         * For example, if you are using DiffUtil with [RecyclerView], you can return the
         * particular field that changed in the item and your [RecyclerView.ItemAnimator] can
         * use that information to run the correct animation.
         *
         *
         * Default implementation returns `null`.
         *
         * @return A payload object that represents the change between the two items.
         */
        open fun getChangePayload( oldItem: T, newItem: T ): Any? = null
    }
}