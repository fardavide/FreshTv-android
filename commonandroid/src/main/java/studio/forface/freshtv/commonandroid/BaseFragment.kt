package studio.forface.freshtv.commonandroid

import androidx.fragment.app.Fragment

/**
 * @author Davide Giuseppe Farella.
 * A base class for [Fragment]'s that lets us add some common parameters that we will be handled
 * from the main Activity, like Toolbar title or FloatingActionButton.
 *
 * Inherit from [Fragment].
 */
abstract class BaseFragment: Fragment(), AndroidComponent