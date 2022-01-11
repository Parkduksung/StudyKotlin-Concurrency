package com.example.chapter4.suspend

import android.util.Log
import kotlinx.coroutines.runBlocking

object Exam1 {

    fun getProfile() = runBlocking {
        val clinet: ProfileServiceRepository = ProfileServiceClient()
        val profile = clinet.asyncFetchById(12).await()
        Log.d("결과", profile.toString())
    }

}