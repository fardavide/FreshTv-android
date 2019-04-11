package studio.forface.freshtv.about.ui

import android.graphics.PorterDuff
import android.graphics.Typeface.BOLD
import android.text.Spannable.SPAN_INCLUSIVE_INCLUSIVE
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import studio.forface.freshtv.about.R
import studio.forface.freshtv.about.ui.ExpandableAdapter.State.COLLAPSED
import studio.forface.freshtv.about.ui.ExpandableAdapter.State.EXPANDED
import studio.forface.freshtv.commonandroid.utils.getThemeColor
import studio.forface.freshtv.commonandroid.utils.inflate
import studio.forface.freshtv.domain.utils.EMPTY_STRING
import studio.forface.freshtv.domain.utils.handle

/**
 * A [RecyclerView.Adapter] that shows max 3 items when collapsed, else all of them
 *
 * @author Davide Giuseppe Farella
 */
internal class ExpandableAdapter(
    private val entries: Array<String>
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
            val positionStart = oldCount - 1 // The position before the footer
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

            private companion object {
                const val START_BOLD = "%1"
                const val END_BOLD = "%2"
            }

            /** @see ViewHolder.onBind */
            override fun onBind( value: String ) {
                // Get the raw String
                var rawString = itemView.context.getString( R.string.about_list_bullet_arg, value )

                // Get the index where to start the Bold and remove the identifier
                val startBold = rawString.indexOf( START_BOLD )
                rawString = rawString.replace( START_BOLD, EMPTY_STRING )

                // Get the index where to end the Bold and remove the identifier
                val endBold = rawString.indexOf( END_BOLD )
                rawString = rawString.replace( END_BOLD, EMPTY_STRING )

                // Build the Spannable
                val spannable = SpannableString( rawString ).apply {
                    handle { setSpan( StyleSpan( BOLD ), startBold, endBold, SPAN_INCLUSIVE_INCLUSIVE ) }
                }

                textView.text = spannable
            }
        }

        /** A [ViewHolder] for the Footer */
        internal class Footer( itemView: View ) : ViewHolder<State>( itemView ) {

            /** The [ImageView] from [itemView] */
            private val imageView = itemView.findViewById<ImageView>( R.id.image )

            /** @see ViewHolder.onBind */
            override fun onBind( value: State ) {

                val text = if ( value == COLLAPSED ) R.string.action_expand else R.string.action_collapse
                textView.setText( text )

                val drawable = with( itemView.context ) {
                    getDrawable( R.drawable.ic_down_arrow_black )!!.apply {
                        setColorFilter( getThemeColor( android.R.attr.colorPrimary ), PorterDuff.Mode.SRC_ATOP )
                    }
                }
                imageView.setImageDrawable( drawable )
                val rotation = if ( value == EXPANDED ) 180f else 360f
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