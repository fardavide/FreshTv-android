package studio.forface.freshtv.commonandroid.frameworkcomponents

import androidx.fragment.app.Fragment

/**
 * @author Davide Giuseppe Farella.
 * A base class for [Fragment]'s that lets us add some common parameters that we will be handled
 * from the main Activity, like Toolbar title or FloatingActionButton.
 *
 * Inherit from [Fragment].
 * Implements [AndroidUiComponent]
 */
abstract class BaseFragment: Fragment(), AndroidUiComponent