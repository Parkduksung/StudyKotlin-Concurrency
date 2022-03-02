package com.example.sample.data.source.remote

import com.example.sample.data.model.Article
import com.example.sample.data.model.Feed
import com.example.sample.util.Result
import com.example.sample.xml.FeedListener
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class ArticleRemoteDataSourceImplTest {

    private lateinit var articleRemoteDataSourceImpl: ArticleRemoteDataSourceImpl

    private val feedListener = mock<FeedListener>()

    @Before
    fun setUp() {
        articleRemoteDataSourceImpl = ArticleRemoteDataSourceImpl(feedListener)
    }

    @Test
    fun getArticlesSuccessTest() = runBlocking {

        //given
        whenever(feedListener.getArticles(mockFeed)).thenReturn(mockArticleList)

        //when, then
        assertEquals(articleRemoteDataSourceImpl.getArticles(mockFeed), Result.Success(mockArticleList))

    }

    @Test
    fun getArticlesFailTest() = runBlocking {

        //given
        whenever(feedListener.getArticles(mockFeed)).then { Exception() }

        //when, then
        assertEquals(
            articleRemoteDataSourceImpl.getArticles(mockFeed).javaClass,
            Result.Error(java.lang.Exception()).javaClass
        )
    }

    companion object {
        val mockFeed = Feed(name = "npr", url = "https://www.npr.org/rss/rss.php?id=1001")

        val mockArticleList = listOf(
            Article(
                feed = "npr",
                title = "Democrat Rep. Rashida Tlaib delivers a response to Biden's State of the Union, ",
                summary = "Her speech detailed a progressive political vision for the future, from lowering prescription drug costs to making major investments in tackling climate change and enshrining abortion access."
            )
        )
    }

}