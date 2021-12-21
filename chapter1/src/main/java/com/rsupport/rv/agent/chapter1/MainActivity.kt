package com.rsupport.rv.agent.chapter1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkTime()

    }

    private fun checkTime() = runBlocking {
        Log.d("결과 - startActiveThread ", Thread.activeCount().toString())
        val time = measureTimeMillis {
            createCoroutines(10)
        }
        Log.d("결과 - checkTime", time.toString())
        Log.d("결과 - endActiveThread ", Thread.activeCount().toString())
    }

    private suspend fun createCoroutines(amount: Int) {
        val jobs = ArrayList<Job>()
        for (i in 1..amount) {
            jobs += CoroutineScope(Dispatchers.IO).launch {
                Log.d("결과 - startThread ", Thread.currentThread().name)
                delay(1000L)
                Log.d("결과 - finishThread ", Thread.currentThread().name)
            }
        }
        jobs.forEach {
            it.join()
        }
    }
}