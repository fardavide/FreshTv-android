package studio.forface.freshtv.preferences.entries

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * A [PreferenceItem] for clean all the `Channel`s and `Group`s
 * It receives a [Boolean] representing whether there are `Channel`s to clean up
 *
 * Inherit from [FrameLayout]
 *
 * @author Davide Giuseppe Farella
 */
internal class CleanChannelsView: FrameLayout, PreferenceItem<Boolean, Unit> {

    private var

    /* OVERRIDING CONSTRUCTORS */
    constructor( context: Context ): super( context )
    constructor( context: Context, attrs: AttributeSet? ) : super( context, attrs )
    constructor( context: Context, attrs: AttributeSet?, defStyleAttr: Int ) : super( context, attrs, defStyleAttr )
    constructor( context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int ) :
            super( context, attrs, defStyleAttr, defStyleRes )


}