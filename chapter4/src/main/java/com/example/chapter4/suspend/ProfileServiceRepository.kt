package com.example.chapter4.suspend

interface ProfileServiceRepository {
    suspend fun asyncFetchByName(name: String): Profile
    suspend fun asyncFetchById(id: Long): Profile
}