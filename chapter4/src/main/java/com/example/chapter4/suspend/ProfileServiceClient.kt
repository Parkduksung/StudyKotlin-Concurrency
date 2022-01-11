package com.example.chapter4.suspend

class ProfileServiceClient : ProfileServiceRepository {

    override suspend fun asyncFetchByName(name: String): Profile {
        return Profile(1, name, 28)
    }

    override suspend fun asyncFetchById(id: Long): Profile {
        return Profile(id, "Susan", 28)
    }

}