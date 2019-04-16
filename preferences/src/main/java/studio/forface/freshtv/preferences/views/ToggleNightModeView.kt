package studio.forface.freshtv.preferences.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_clean_guides.view.*
import kotlinx.android.synthetic.main.view_toggle_night_mode.view.*
import studio.forface.freshtv.commonandroid.utils.addView
import studio.forface.freshtv.commonandroid.utils.getText
import studio.forface.freshtv.domain.invoke
import studio.forface.freshtv.preferences.R
import studio.forface.freshtv.preferences.uimodels.GuidesDatabaseStateUiModel
import studio.forface.freshtv.preferences.uimodels.GuidesDatabaseStateUiModel.*

/**
 * A [PreferenceItem] for toggle Night Mode
 * It receives a [Boolean] representing whether there are Night Mode is enabled
 *
 * Inherit from [FrameLayout]
 *
 * @author Davide Giuseppe Farella
 */
internal class ToggleNightModeView : FrameLayout, PreferenceItem<Boolean, Unit> {

    /**
     * A [Boolean] representing whether the Night Mode is enabled
     * Default is `true`
     */
    private var nightModeState: Boolean = true
        set( value ) {
            field = value
            prefToggleNightModeDescriptionTextView.text = when (value) {
                true -> enabledDescription
                false -> disabledDescription
            }
        }

    /** A [CharSequence] for [prefToggleNightModeDescriptionTextView] when disabled */
    private val disabledDescription = getText( R.string.toggle_night_mode_description_disabled )

    /** A [CharSequence] for [prefToggleNightModeDescriptionTextView] when enabled */
    private val enabledDescription = getText( R.string.toggle_night_mode_description_enabled )

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
        addView( R.layout.view_toggle_night_mode )
        // Re-set nightModeState for set View's
        nightModeState = nightModeState
        setOnClickListener { onAction() }
    }

    /** Update [nightModeState] with the given [value] */
    override fun updateValue( value: Boolean ) {
        nightModeState = value
    }

    /** @see PreferenceItem.doOnAction */
    override fun doOnAction( block: (Unit) -> Unit ) {
        onAction = block
    }
}