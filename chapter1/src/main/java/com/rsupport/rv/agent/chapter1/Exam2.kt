package com.rsupport.rv.agent.chapter1

import android.util.Log
import kotlinx.coroutines.*

object Exam2 {

    suspend fun getProfile() {
        CoroutineScope(Dispatchers.IO).launch {
            val asyncGetUserInfo =  async { asyncGetUserInfo() }
            val asyncGetContactInfo = async {asyncGetContactInfo() }
            createUser(asyncGetUserInfo.await(), asyncGetContactInfo.await())
        }
    }

    private suspend fun asyncGetUserInfo(): String {
        delay(200L)
        return "UserInfo"
    }

    private suspend fun asyncGetContactInfo(): String {
        delay(5000L)
        return "ContactInfo"
    }

    private fun createUser(userInfo: String, contactInfo: String) {
        Log.d("결과", "$userInfo and $contactInfo")
    }

}