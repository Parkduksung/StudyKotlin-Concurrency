package com.example.practice1.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.practice1.api.BookItem
import com.example.practice1.api.KakaoBookSearchService

class KakaoBookSearchDataSource(
    private val query: String,
    private val kakaoBookSearchService: KakaoBookSearchService
) : PagingSource<Int, BookItem>() {

    override fun getRefreshKey(state: PagingState<Int, BookItem>): Int? {
        return state.anchorPosition?.let {
            val closestPageToPosition = state.closestPageToPosition(it)
            closestPageToPosition?.prevKey?.plus(defaultDisplay)
                ?: closestPageToPosition?.nextKey?.minus(defaultDisplay)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookItem> {
        val start = params.key ?: defaultStart

        return try {
            val response =
                kakaoBookSearchService.getBooks(query = query, page = start, size = defaultDisplay)

            val items = response.bookItems

            val nextKey = if (items.isEmpty()) {
                null
            } else {
                start + params.loadSize
            }
            val prevKey = if (start == defaultStart) {
                null
            } else {
                start - defaultDisplay
            }


            LoadResult.Page(items, prevKey, nextKey)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        const val defaultStart = 1
        const val defaultDisplay = 50
    }
}