package com.example.sample.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample.data.model.Article
import com.example.sample.data.model.Feed
import com.example.sample.data.repo.ArticleRepository
import com.example.sample.util.Result
import com.example.sample.util.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(articleRepository: ArticleRepository) :
    ViewModel() {

    private val _articleViewStateLiveData = MutableLiveData<ArticleViewState>()
    val articleViewStateLiveData: LiveData<ArticleViewState> = _articleViewStateLiveData

    private val getArticles: Flow<Result<List<Article>>> = articleRepository.getArticles(
        Feed(
            name = "npr",
            url = "https://www.npr.org/rss/rss.php?id=1001"
        )
    ).asResult()

    private val getArticles1: Flow<Result<List<Article>>> = articleRepository.getArticles(
        Feed(
            name = "npr",
            url = "https://www.npr.org/rss/rss.php?id=1001"
        )
    ).asResult()

    val uiState: StateFlow<ArticleScreenState> =
        combine(getArticles, getArticles1) { articleResult, _ ->
            val articles: ArticleViewState = when (articleResult) {
                is Result.Success -> ArticleViewState.GetArticles(articleResult.data)
                is Result.Loading -> ArticleViewState.Loading
                is Result.Error -> ArticleViewState.Error
            }
            ArticleScreenState(articles)
        }.stateIn(
            CoroutineScope(Dispatchers.IO),
            started = SharingStarted.WhileSubscribed(5_000),
            ArticleScreenState(ArticleViewState.Loading)
        )

}

sealed interface ArticleViewState {
    object Loading : ArticleViewState
    data class GetArticles(val list: List<Article>) : ArticleViewState
    object Error : ArticleViewState
}

data class ArticleScreenState(
    val articleState: ArticleViewState
)
