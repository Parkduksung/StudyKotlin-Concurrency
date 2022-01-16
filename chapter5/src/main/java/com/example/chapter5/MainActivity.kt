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

        val producerFibonacci = Exam3.producerFibonacci()

        GlobalScope.launch {
            producerFibonacci.consumeEach {
                if(it > 100L){
                    producerFibonacci.cancel()
                }else{
                    Log.d("결과", it.toString())
                }
            }
        }
    }
}