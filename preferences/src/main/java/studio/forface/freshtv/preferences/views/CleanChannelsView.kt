package studio.forface.freshtv.preferences.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_clean_channels.view.*
import studio.forface.freshtv.commonandroid.utils.addView
import studio.forface.freshtv.commonandroid.utils.getText
import studio.forface.freshtv.domain.invoke
import studio.forface.freshtv.preferences.R
import studio.forface.freshtv.preferences.uimodels.ChannelsDatabaseStateUiModel
import studio.forface.freshtv.preferences.uimodels.ChannelsDatabaseStateUiModel.*

/**
 * A [PreferenceItem] for clean all the `Channel`s and `Group`s
 * It receives a [Boolean] representing whether there are `Channel`s to clean up
 *
 * Inherit from [FrameLayout]
 *
 * @author Davide Giuseppe Farella
 */
internal class CleanChannelsView : FrameLayout, PreferenceItem<ChannelsDatabaseStateUiModel, Unit> {

    /**
     * A [Boolean] representing whether the `Channel`s can be cleared.
     * Default is false
     */
    private var databaseState: ChannelsDatabaseStateUiModel = Empty
        set( value ) {
            field = value
            prefCleanChannelsTitleTextView.isEnabled = value != Empty
            prefCleanChannelsDescriptionTextView.isEnabled = value != Empty
            prefCleanChannelsDescriptionTextView.text = when (value) {
                NotEmpty -> enabledDescription
                Empty -> disabledDescription
            }
        }

    /** A [CharSequence] for [prefCleanChannelsDescriptionTextView] when disabled */
    private val disabledDescription = getText( R.string.clean_channels_description_empty )

    /** A [CharSequence] for [prefCleanChannelsDescriptionTextView] when enabled */
    private val enabledDescription = getText( R.string.clean_channels_description_can_clear )

    /**
     * A lambda that will be invoked when action need to be invoke
     * Note: usually that shouldn't be invoked on [updateValue]
     */
    private var onAction: (Unit) -> Unit = {}

    /* OVERRIDING CONSTRUCTORS */
    constructor( context: Context ): super( context ) {
        init()
    }
    constructor( context: Context, attrs: AttributeSet? ) : super( context, attrs ) {
        init()
    }
    constructor( context: Context, attrs: AttributeSet?, defStyleAttr: Int ) : super( context, attrs, defStyleAttr ) {
        init()
    }
    constructor( context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int ) :
            super( context, attrs, defStyleAttr, defStyleRes ) {
        init()
    }

    private fun init() {
        addView( R.layout.view_clean_channels )
        // Re-set databaseState for set View's
        databaseState = Empty
        setOnClickListener { if ( databaseState == NotEmpty ) { onAction() } }
    }

    /** Update [databaseState] with the given [value] */
    override fun updateValue( value: ChannelsDatabaseStateUiModel ) {
        databaseState = value
    }

    /** @see PreferenceItem.doOnAction */
    override fun doOnAction( block: (Unit) -> Unit ) {
        onAction = block
    }
}