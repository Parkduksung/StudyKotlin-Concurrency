package com.example.sample.data.repo

import com.example.sample.data.source.remote.ArticleRemoteDataSource
import com.example.sample.data.source.remote.ArticleRemoteDataSourceImplTest.Companion.mockArticleList
import com.example.sample.data.source.remote.ArticleRemoteDataSourceImplTest.Companion.mockFeed
import com.example.sample.util.Result
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ArticleRepositoryImplTest {

    private lateinit var articleRepositoryImpl: ArticleRepositoryImpl

    private val articleRemoteDataSource = mock<ArticleRemoteDataSource>()

    @Before
    fun setUp() {
        articleRepositoryImpl = ArticleRepositoryImpl(articleRemoteDataSource)
    }

    @Test
    fun getArticlesSuccessTest() = runBlocking {

        val successResult = Result.Success(mockArticleList)

        //given
        whenever(articleRemoteDataSource.getArticles(mockFeed)).thenReturn(successResult)

        //when, then
        assertEquals(articleRepositoryImpl.getArticles(mockFeed), successResult)

    }

    @Test
    fun getArticlesFailTest() = runBlocking {

        val failureResult = Result.Error(Exception())

        //given
        whenever(articleRemoteDataSource.getArticles(mockFeed)).then { failureResult }

        //when, then
        assertEquals(articleRepositoryImpl.getArticles(mockFeed), failureResult)
    }

}