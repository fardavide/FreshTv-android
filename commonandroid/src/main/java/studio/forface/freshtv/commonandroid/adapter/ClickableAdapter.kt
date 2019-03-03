package studio.forface.freshtv.commonandroid.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import studio.forface.freshtv.commonandroid.frameworkcomponents.AndroidUiComponent

/**
 * @author Davide Giuseppe Farella.
 * A common interface for Adapters that has clickable items [T]
 */
interface ClickableAdapter<T> {

    /** A callback that will be triggered when an item is clicked */
    var onItemClick: (T) -> Unit

    /**
     * An invoker for [onItemClick], we use it so the [ViewHolder] will always use the updated
     * [onItemClick] even if it changes after the [ViewHolder] is created.
     */
    val clickListenerInvoker: (T) -> Unit get() = { onItemClick( it ) }

    /** A callback that will be triggered when an item is long clicked */
    var onItemLongClick: (T) -> Unit

    /**
     * An invoker for [onItemLongClick], we use it so the [ViewHolder] will always use the updated
     * [onItemLongClick] even if it changes after the [ViewHolder] is created.
     */
    val longClickListenerInvoker: (T) -> Unit get() = { onItemLongClick( it ) }

    /** Prepare the given [ViewHolder] with click listeners */
    private fun prepareViewHolder( holder: ViewHolder<T> ) {
        holder.clickListenerInvoker = this.clickListenerInvoker
        holder.longClickListenerInvoker = this.longClickListenerInvoker
    }

    /** Calls private [prepareViewHolder] on the receiver [ViewHolder] */
    fun ViewHolder<T>.prepareClickListeners() {
        prepareViewHolder(this )
    }

    /** A base [RecyclerView.ViewHolder] for [ClickableAdapter] implementations */
    abstract class ViewHolder<T>( itemView: View): RecyclerView.ViewHolder( itemView ), AndroidUiComponent {

        internal var clickListenerInvoker: (T) -> Unit = {}
        internal var longClickListenerInvoker: (T) -> Unit = {}

        /**
         * Populate the [View] with the given [item] [T].
         */
        open fun onBind( item: T ) {
            itemView.setOnClickListener { clickListenerInvoker( item ) }
            itemView.setOnLongClickListener {
                longClickListenerInvoker( item )
                true
            }
        }
    }
}