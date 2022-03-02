package com.example.sample.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.sample.data.repo.ArticleRepository
import com.example.sample.data.source.remote.ArticleRemoteDataSourceImplTest.Companion.mockArticleList
import com.example.sample.data.source.remote.ArticleRemoteDataSourceImplTest.Companion.mockFeed
import com.example.sample.util.Result
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ArticleViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var articleViewModel: ArticleViewModel

    private val articleRepository : ArticleRepository = mock()

    private val viewStateObserver: Observer<ArticleViewModel.ArticleViewState> = mock()

    @Before
    fun setUp(){
        Dispatchers.setMain(TestCoroutineDispatcher())
        articleViewModel = ArticleViewModel(articleRepository)
        articleViewModel.articleViewStateLiveData.observeForever(viewStateObserver)
    }


    @Test
    fun getArticlesSuccessTest() = runBlocking {

        val successResult = Result.Success(mockArticleList)

        //given
        Mockito.`when`(articleRepository.getArticles(mockFeed)).thenReturn(successResult)

        //when
        articleViewModel.getArticles(mockFeed)

        delay(500L)
//        //then
        Mockito.verify(viewStateObserver)
            .onChanged(ArticleViewModel.ArticleViewState.GetArticles(successResult.data))
    }

    @Test
    fun getArticlesFailTest() = runBlocking {

        val failureResult = Result.Error(Exception())

        //given
        whenever(articleRepository.getArticles(mockFeed)).then { failureResult }

        //when
        articleViewModel.getArticles(mockFeed)

        delay(500L)
//        //then
        Mockito.verify(viewStateObserver)
            .onChanged(ArticleViewModel.ArticleViewState.Error)
    }


}