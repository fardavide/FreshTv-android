package studio.forface.freshtv.domain.utils

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Test class for collections
 *
 * @author Davide Giuseppe Farella
 */
class CollectionsTest {

    @Test
    fun filterAsync() = runBlocking {
        val ints = 1..10
        val predicate: suspend (Int) -> Boolean = { delay(100 );it % 2 == 0 }

        var result = listOf<Int>()
        val asyncTime = measureTimeMillis { result = ints.filterAsync( predicate ) }

        assertEquals( listOf( 2, 4, 6, 8, 10 ), result )
        assert( asyncTime in 100..150 )
    }

    @Test
    fun forEachAsync() = runBlocking {
        val task: suspend () -> Unit = { delay(100 ) }
        val tasks = ( 1..10 ).map { task }

        val sequentialTime = measureTimeMillis { tasks.forEach { it() } }
        val asyncTime = measureTimeMillis { tasks.forEachAsync { it() } }

        assert( sequentialTime in 1000..1050 )
        assert( asyncTime in 100..150 )
    }

    @Test
    fun mapAsync() = runBlocking {
        val ints = 1..5
        val mapper: suspend (Int) -> Int = { delay(100 );it * 2 }

        var result = listOf<Int>()
        val asyncTime = measureTimeMillis { result = ints.mapAsync( mapper ) }

        assertEquals( result, listOf( 2, 4, 6, 8, 10 ) )
        assert( asyncTime in 100..150 )
    }
}