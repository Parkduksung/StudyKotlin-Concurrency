package com.example.chapter5

object Exam2 {

    fun sequenceExam1() = sequence<Any> {
        yield("a")
        yield(1)
        yield(32L)
    }

    fun sequenceExam2() = sequence {
        yield(1)
        yield(1)
        yield(2)
        yield(3)
        yield(5)
        yield(8)
        yield(13)
        yield(21)
    }

    fun fibonacci() = sequence {
        var current = 1L
        var next = 1L

        while (true) {
            yield(next)
            val tmpNext = current + next
            current = next
            next = tmpNext
        }
    }
}