package com.example.chapter5

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.produce

object Exam3 {

    fun producerExam1(dispatcher: CoroutineDispatcher) = GlobalScope.produce(dispatcher) {
        for (i in 0..9) {
            send(i)
        }
    }

    fun producerFibonacci() = GlobalScope.produce {
        send(1L)
        var current = 1L
        var next = 1L
        while (true) {
            send(next)
            val tmpNext = current + next
            current = next
            next = tmpNext
        }
    }
}