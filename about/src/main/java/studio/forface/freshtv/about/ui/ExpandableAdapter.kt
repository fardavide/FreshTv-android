package studio.forface.freshtv.about.ui

import android.graphics.PorterDuff
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import studio.forface.freshtv.about.R
import studio.forface.freshtv.about.ui.ExpandableAdapter.State.*
import studio.forface.freshtv.commonandroid.utils.getThemeColor
import studio.forface.freshtv.commonandroid.utils.inflate

/**
 * A [RecyclerView.Adapter] that shows max 3 items when collapsed, else all of them
 *
 * @author Davide Giuseppe Farella
 */
internal class ExpandableAdapter(
    private val entries: List<String>
): RecyclerView.Adapter<ExpandableAdapter.ViewHolder<*>>() {

    /** The [ExpandableAdapter.State] of the Adapter */
    private var state: State = COLLAPSED
        set( value ) {
            val oldCount = itemCount
            field = value
            notifyStateChanged( oldCount )
        }

    /** @return [entries]'s size + footer if [EXPANDED] else max 3 + footer */
    override fun getItemCount(): Int {
        val partial = if ( state == EXPANDED ) entries.size
        else entries.size.coerceAtMost(3 )

        return partial + 1 // 1 is footer
    }

    /** @return [VIEW_TYPE_FOOTER] if last element, else [VIEW_TYPE_ENTRY] */
    override fun getItemViewType( position: Int ) =
        if ( position == itemCount - 1 ) VIEW_TYPE_FOOTER else VIEW_TYPE_ENTRY

    /** Update items when [state] changed */
    private fun notifyStateChanged( oldCount: Int ) {
        val newCount = itemCount

        // Update the footer
        notifyItemChanged(oldCount - 1 )

        if ( newCount > oldCount ) {
            val positionStart = oldCount - 2 // The position before the footer
            val insertionCount = newCount - oldCount
            // Notify insertions
            notifyItemRangeInserted( positionStart, insertionCount )

        } else if ( oldCount > newCount ) {
            val positionStart = newCount - 1 // The position of the item that will be before the footer
            val removalCount = oldCount - newCount
            // Notify removals
            notifyItemRangeRemoved( positionStart, removalCount )
        }
    }

    /** @see [RecyclerView.Adapter.onCreateViewHolder] */
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): ViewHolder<*> {
        return when ( viewType ) {

            VIEW_TYPE_ENTRY -> ViewHolder.Entry( parent.inflate( R.layout.item_about_entry ) )

            VIEW_TYPE_FOOTER -> {
                val view = parent.inflate( R.layout.item_about_footer ).apply {
                    setOnClickListener { toggleState() }
                }
                ViewHolder.Footer( view )
            }
            else -> throw AssertionError("Wrong viewType: $viewType" )
        }
    }

    /** @see [RecyclerView.Adapter.onBindViewHolder] */
    override fun onBindViewHolder( holder: ViewHolder<*>, position: Int ) {
        if ( holder is ViewHolder.Entry ) holder.onBind( entries[position] )
        else if ( holder is ViewHolder.Footer ) holder.onBind( state )
    }

    /** Toggle the [ExpandableAdapter.state] between [COLLAPSED] and [EXPANDED] */
    private fun toggleState() {
        state = if ( state == EXPANDED ) COLLAPSED else EXPANDED
    }

    /** An enum of the possible states of [ExpandableAdapter] */
    internal enum class State { COLLAPSED, EXPANDED }

    /** A sealed class for the [RecyclerView.ViewHolder] for [ExpandableAdapter] */
    internal sealed class ViewHolder<T>( itemView: View ) : RecyclerView.ViewHolder( itemView ) {

        /** The [TextView] from [itemView] */
        protected val textView: TextView = itemView.findViewById( R.id.text )

        /** Bind the given [T] to [itemView] */
        abstract fun onBind( value: T )

        /** A [ViewHolder] for [ExpandableAdapter.entries] */
        internal class Entry( itemView: View ) : ViewHolder<String>( itemView ) {

            /** @see ViewHolder.onBind */
            override fun onBind( value: String ) {
                textView.text = itemView.context.getString( R.string.list_bullet_arg, value )
            }
        }

        /** A [ViewHolder] for the Footer */
        internal class Footer( itemView: View ) : ViewHolder<State>( itemView ) {

            /** The [ImageView] from [itemView] */
            private val imageView = itemView.findViewById<ImageView>( R.id.image )

            /** @see ViewHolder.onBind */
            override fun onBind( value: State ) {

                val text = if ( value == COLLAPSED ) R.string.action_expand else R.string.action_collapse
                textView.setText(text)

                val drawable = with( itemView.context ) {
                    getDrawable( R.drawable.ic_down_arrow_black )!!.apply {
                        setColorFilter( getThemeColor( android.R.attr.colorPrimary ), PorterDuff.Mode.SRC )
                    }
                }
                imageView.setImageDrawable( drawable )
                val rotation = if ( value == EXPANDED ) 180f else 0f
                imageView.animate()
                    .rotation(rotation - imageView.rotation )
                    .start()
            }
        }
    }

    private companion object {
        /** An [Int] representing a ViewType for Entry */
        const val VIEW_TYPE_ENTRY = 0

        /** An [Int] representing a ViewType for Footer */
        const val VIEW_TYPE_FOOTER = 1
    }
}