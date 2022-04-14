package com.example.practice1.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.practice1.api.BookItem
import com.example.practice1.data.KakaoBookSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {

    private val repository = KakaoBookSearchRepository()
    private val queryFlow = MutableSharedFlow<String>()

    val pagingDataFlow = queryFlow.flatMapLatest {
        searchBooks(it)
    }.cachedIn(viewModelScope)

    fun handleQuery(query: String) {
        viewModelScope.launch {
            queryFlow.emit(query)
        }
    }

    private fun searchBooks(query: String): Flow<PagingData<BookItem>> =
        repository.getImageSearch(query)
}