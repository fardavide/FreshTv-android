package studio.forface.freshtv.settings.helper

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import kotlin.test.*


class SettingsTest {
    private val settings by lazy {
        val context = InstrumentationRegistry.getInstrumentation().context
        val pref = context.getSharedPreferences("test", Context.MODE_PRIVATE )

        Settings( pref )
    }

    @BeforeTest
    fun clearSettings() {
        settings.clear()
    }

    @Test
    fun clear() {
        settings.putInt("a", 5)
        settings.clear()
        assertEquals(-1, settings.getInt("a", -1))
    }

    @Test
    fun remove() {
        settings.putInt("a", 3)
        settings.remove("a")
        assertEquals(-1, settings.getInt("a", -1))
    }

    @Test
    fun contains() {
        assertFalse(settings.hasKey("a"))
        settings.putString("a", "value")
        assertTrue(settings.hasKey("a"))
    }

    @Test
    fun reifiedDelegate() {
        var a by settings( defaultValue = 5 )
        assertEquals(5, a )
        a = 2
        assertEquals(2, a )
        a = 0
        assertEquals(0, a )
    }

    @Test
    fun nullableReifiedDelegate() {
        var a by settings<Int?>()
        assertEquals(null, a )
        a = 2
        assertEquals(2, a )
        a = 0
        assertEquals(0, a )
        a = null
        assertEquals(null, a )
    }

    @Test
    fun listener() {
        val sleep = 1L
        var invocationCount = 0
        val callback = { invocationCount += 1 }

        // No invocation for call before listener was set
        settings["a"] = 2
        val listener = settings.addListener("a", callback )
        Thread.sleep( sleep )
        assertEquals(0, invocationCount)

        // No invocation on set to existing value
        settings["a"] = 2
        Thread.sleep( sleep )
        assertEquals(0, invocationCount)

        // New invocation on value change
        settings["a"] = 1
        Thread.sleep( sleep )
        assertEquals(1, invocationCount)

        // No invocation if value unchanged
        settings["a"] = 1
        Thread.sleep( sleep )
        assertEquals(1, invocationCount)

        // New invocation on remove
        settings -= "a"
        Thread.sleep( sleep )
        assertEquals(2, invocationCount)

        // New invocation on re-add with same value
        settings["a"] = 1
        Thread.sleep( sleep )
        assertEquals(3, invocationCount)

        // No invocation on other key change
        settings["b"] = 1
        Thread.sleep( sleep )
        assertEquals(3, invocationCount)

        // New invocation on clear
        settings.clear()
        Thread.sleep( sleep )
        assertEquals(4, invocationCount)

        // Second listener at the same key also gets called
        var invokationCount2 = 0
        val callback2 = { invokationCount2 += 1 }
        settings.addListener("a", callback2)
        settings["a"] = 3
        Thread.sleep( sleep )
        assertEquals(5, invocationCount)
        assertEquals(1, invokationCount2)

        // No invocation on listener which is removed
        settings.removeListener(listener)
        settings["a"] = 2
        Thread.sleep( sleep )
        assertEquals(5, invocationCount)
        assertEquals(2, invokationCount2)
    }
}
