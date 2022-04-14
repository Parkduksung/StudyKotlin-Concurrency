package com.example.practice1.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.practice1.api.BookItem
import com.example.practice1.api.KakaoBookSearchService
import com.example.practice1.data.KakaoBookSearchDataSource.Companion.defaultDisplay
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KakaoBookSearchRepository {
    private val service: KakaoBookSearchService = Retrofit.Builder()
        .baseUrl("https://dapi.kakao.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(KakaoBookSearchService::class.java)
    fun getImageSearch(query: String): Flow<PagingData<BookItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = defaultDisplay,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                KakaoBookSearchDataSource(query, service)
            }
        ).flow
    }
}