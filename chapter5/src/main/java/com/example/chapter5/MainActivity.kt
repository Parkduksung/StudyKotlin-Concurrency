package com.example.chapter5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

class MainActivity : AppCompatActivity() {

    private val dispatcher = newSingleThreadContext("myThread")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val producer = Exam3.producerExam1(dispatcher)

        GlobalScope.launch {


            // 0 출력.
//            Log.d("결과", producer.receive().toString())

            //0 - 9 까지 출력
//            producer.consumeEach {
//                Log.d("결과", it.toString())
//            }

            //한번더 출력하게되면 error 발생
            //cause : Channel was closed
//            Log.d("결과", producer.receive().toString())
        }
    }
}