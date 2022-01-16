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

        val iterator = Exam1.yieldExam2()

        for (i in 0..5) {

            if (iterator.hasNext()) {
                Log.d("결과" , "$i is ${iterator.next()}")
            } else {
                Log.d("결과" , "No more elements")
            }

        }

    }
}