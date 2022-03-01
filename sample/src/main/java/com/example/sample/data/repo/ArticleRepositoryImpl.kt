package com.example.sample.data.repo

import com.example.sample.data.model.Article
import com.example.sample.data.model.Feed
import com.example.sample.data.source.remote.ArticleRemoteDataSource
import com.example.sample.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(private val articleRemoteDataSource: ArticleRemoteDataSource) :
    ArticleRepository {

    override suspend fun getArticles(feed: Feed): Result<List<Article>> = withContext(Dispatchers.IO) {
        return@withContext articleRemoteDataSource.getArticles(feed)
    }
}