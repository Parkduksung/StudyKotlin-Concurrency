package com.example.practice1.api

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoBookSearchService {

    @Headers("Authorization: KakaoAK $REST_API_KEY")
    @GET("/v3/search/book")
    suspend fun getBooks(
        @Query("query") query: String,
        @Query("sort") sort: String? = null,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
    ): KakaoBookSearchResponse

    companion object{
        private const val REST_API_KEY = "7f0bc613532236da7fe88cf3b1bc3160"
    }
}