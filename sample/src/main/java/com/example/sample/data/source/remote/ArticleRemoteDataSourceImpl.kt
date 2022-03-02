package com.example.sample.data.source.remote

import com.example.sample.data.model.Article
import com.example.sample.data.model.Feed
import com.example.sample.util.Result
import com.example.sample.xml.FeedListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticleRemoteDataSourceImpl @Inject constructor(private val feedListener: FeedListener) : ArticleRemoteDataSource {

    override suspend fun getArticles(feed: Feed): Result<List<Article>> = withContext(Dispatchers.IO) {
        try {
            val articleList = feedListener.getArticles(feed)
            return@withContext Result.Success(articleList)
        } catch (e: java.lang.Exception) {
            return@withContext Result.Error(e)
        }
    }
}