package com.rsupport.rv.agent.chapter1

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

object Exam3 {

    var count = 0

    fun asyncIncrementTest() = runBlocking {
        val workA = asyncIncrement(2000)
        val workB = asyncIncrement(100)
        workA.await()
        workB.await()

        Log.d("결과 count ", count.toString())
    }

    private fun asyncIncrement(by: Int) = GlobalScope.async {
        for (i in 0 until by) {
            count++
        }
    }

}