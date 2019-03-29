package studio.forface.freshtv.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle.State.*
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.runner.AndroidJUnit4
import kotlinx.android.synthetic.main.activity_main.view.*
import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import studio.forface.freshtv.R
import kotlin.test.Test

@RunWith( AndroidJUnit4::class )
class TvChannelGroupsFragmentTest {

    @Test
    fun test() {
        val fragment = launchFragmentInContainer<TvChannelGroupsFragment>()
        fragment.moveToState( RESUMED )
        onView( withId ( R.id.titleTextView ) ).check( matches( withText("Tv Channels" ) ) )
    }
}