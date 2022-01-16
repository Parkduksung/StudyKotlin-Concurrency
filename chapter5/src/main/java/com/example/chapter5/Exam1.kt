package com.example.chapter5

object Exam1 {

    fun yieldExam1() = iterator {
        yield("First")
        yield("Second")
        yield("Third")
    }

    fun yieldExam2() = iterator {
        for (i in 0..4) {
            yield(i * 4)
        }
    }

    fun yieldExam3() = iterator {
        println("yielding 1")
        yield(1)
        println("yielding 2")
        yield(2)
    }

}