package com.example.chapter4.suspend

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class ProfileServiceClient : ProfileServiceRepository {

    override fun asyncFetchByName(name: String) = GlobalScope.async {
        Profile(1, name, 28)
    }

    override fun asyncFetchById(id: Long) = GlobalScope.async {
        Profile(id, "Susan", 28)
    }

}