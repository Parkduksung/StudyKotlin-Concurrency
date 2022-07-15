package com.example.sample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sample.data.model.Article
import com.example.sample.data.model.Feed
import com.example.sample.domain.use_case.GetArticlesUseCase
import com.example.sample.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val getArticlesUseCase: GetArticlesUseCase) :
    ViewModel() {

    private val _articleViewStateLiveData = MutableLiveData<ArticleViewState>()
    val articleViewStateLiveData: LiveData<ArticleViewState> = _articleViewStateLiveData

    init {
        getArticles(Feed(name = "npr", url = "https://www.npr.org/rss/rss.php?id=1001"))
    }

    private fun getArticles(feed: Feed) {
        getArticlesUseCase(feed).onEach { result ->
            when (result) {
                is Result.Success -> {
                    withContext(Main) {
                        _articleViewStateLiveData.value = ArticleViewState.GetArticles(result.data)
                    }
                }

                is Result.Error -> {
                    withContext(Main) {
                        _articleViewStateLiveData.value = ArticleViewState.Error
                    }
                }
            }
        }.launchIn(CoroutineScope(IO))
    }


    sealed class ArticleViewState {
        data class GetArticles(val list: List<Article>) : ArticleViewState()
        object Error : ArticleViewState()
    }
}