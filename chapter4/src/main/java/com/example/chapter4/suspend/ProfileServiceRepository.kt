package com.example.chapter4.suspend

import kotlinx.coroutines.Deferred

interface ProfileServiceRepository {
    fun asyncFetchByName(name: String): Deferred<Profile>
    fun asyncFetchById(id: Long): Deferred<Profile>
}