package com.example.chapter5

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sequence2 = Exam2.sequenceExam2()

        Log.d("결과", sequence2.take(5).joinToString())


        val sequence = sequence {
            for (i in 0..9) {
                Log.d("결과", "Yielding $i")
                yield(i)
            }
        }

        val fibonacci = Exam2.fibonacci()

        val indexed = fibonacci.take(10).withIndex()

        for ((index, value) in indexed) {
            Log.d("결과", "index : $index , value : $value")
        }

    }
}