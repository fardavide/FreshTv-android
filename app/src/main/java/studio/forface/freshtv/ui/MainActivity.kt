package studio.forface.freshtv.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.toolbar as syntheticToolbar
import kotlinx.android.synthetic.main.activity_main.*
import studio.forface.freshtv.R
import studio.forface.freshtv.commonandroid.frameworkcomponents.BaseActivity

/**
 * @author Davide Giuseppe Farella
 * The Main `Activity` of the app
 *
 * Inherit from [BaseActivity]
 */
class MainActivity: BaseActivity( R.layout.activity_main ) {

    /** @see BaseActivity.navController */
    override val navController: NavController get() = findNavController( R.id.nav_host )

    /** @see BaseActivity.rootView */
    override val rootView: View get() = root

    /** @see BaseActivity.toolbar */
    override val toolbar: Toolbar get() = syntheticToolbar

    /** When the `Activity` is Created */
    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setSupportActionBar( toolbar )
        toolbar.setupWithNavController( navController )
    }
}