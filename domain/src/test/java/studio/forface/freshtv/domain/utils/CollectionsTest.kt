package studio.forface.freshtv.domain.utils

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis
import kotlin.test.Test

/**
 * @author Davide Giuseppe Farella.
 * Test class for collections
 */
class CollectionsTest {

    @Test
    fun forEachAsync() = runBlocking {
        val task: suspend () -> Unit = { delay(100 ) }
        val tasks = ( 1..10 ).map { task }

        val sequentialTime = measureTimeMillis { tasks.forEach { it() } }
        val asyncTime = measureTimeMillis { tasks.forEachAsync { it() } }

        assert( sequentialTime in 1000..1050 )
        assert( asyncTime in 100..150 )
    }
}