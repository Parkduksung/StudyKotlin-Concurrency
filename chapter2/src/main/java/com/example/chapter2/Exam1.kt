package com.example.chapter2

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

object Exam1 {

    fun start() = runBlocking {
        val dispatcher = newSingleThreadContext(name = "ServiceCall")
        val task = GlobalScope.launch(dispatcher) {
            printCurrentThread()
        }
        task.join()
    }

    private fun printCurrentThread() {
        println("Running in thread [${Thread.currentThread().name}]")
    }

}