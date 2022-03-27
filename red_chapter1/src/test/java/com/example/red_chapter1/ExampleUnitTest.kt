package com.example.red_chapter1

import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    //돌아가는 스레드가 여기선 test worker 이다.
    @Test
    fun `간단한 코루틴`() = runBlocking {
        println(Thread.currentThread().name)
        println("Hello!")
    }


}