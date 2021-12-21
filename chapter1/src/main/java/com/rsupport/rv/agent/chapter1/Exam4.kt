package com.rsupport.rv.agent.chapter1

import android.util.Log
import kotlinx.coroutines.*

object Exam4 {

    private lateinit var job1: Job
    private lateinit var job2: Job

    fun testCircularDependency() = runBlocking {
        job1 = GlobalScope.launch {
            delay(1000L)
            job2.join()
        }

        job2 = GlobalScope.launch {
            job1.join()
        }

        job1.join()
        Log.d("결과", "finished")
    }

}