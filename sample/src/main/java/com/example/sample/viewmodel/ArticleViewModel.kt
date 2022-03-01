package com.example.sample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sample.data.model.Article
import com.example.sample.data.model.Feed
import com.example.sample.data.repo.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val articleRepository: ArticleRepository) : ViewModel() {

    private val _articleViewStateLiveData = MutableLiveData<ArticleViewState>()
    val articleViewStateLiveData: LiveData<ArticleViewState> = _articleViewStateLiveData

    fun getArticles(feed: Feed) {
        CoroutineScope(Dispatchers.IO).launch {

            when (val result = articleRepository.getArticles(feed)) {
                is com.example.sample.util.Result.Success -> {
                    _articleViewStateLiveData.value = ArticleViewState.GetArticles(result.data)
                }

                is com.example.sample.util.Result.Error -> {
                    _articleViewStateLiveData.value = ArticleViewState.Error
                }
            }
        }
    }


    sealed class ArticleViewState {
        data class GetArticles(val list: List<Article>) : ArticleViewState()
        object Error : ArticleViewState()
    }
}