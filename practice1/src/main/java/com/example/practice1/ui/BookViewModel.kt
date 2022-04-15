package com.example.practice1.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.practice1.api.BookItem
import com.example.practice1.data.KakaoBookSearchRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {

    private val repository = KakaoBookSearchRepository()
    private val queryFlow = MutableSharedFlow<String>()

    private val _toggleItem = MutableSharedFlow<BookItem>()

    val toggleItem = _toggleItem.asSharedFlow()

    val pagingDataFlow = queryFlow.flatMapLatest {
        searchBooks(it)
    }.cachedIn(viewModelScope)

    fun handleQuery(query: String) {
        viewModelScope.launch {
            queryFlow.emit(query)
        }
    }

    fun toggle(item: BookItem) {
        viewModelScope.launch {
            _toggleItem.emit(item)
        }
    }

    private fun searchBooks(query: String): Flow<PagingData<BookItem>> =
        repository.getImageSearch(query)
}