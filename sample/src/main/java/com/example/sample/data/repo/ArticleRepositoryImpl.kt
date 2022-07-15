package com.example.sample.data.repo

import com.example.sample.data.model.Article
import com.example.sample.data.model.Feed
import com.example.sample.data.source.remote.ArticleRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(private val articleRemoteDataSource: ArticleRemoteDataSource) :
    ArticleRepository {

    override fun getArticles(feed: Feed): Flow<List<Article>> =
        articleRemoteDataSource.getArticles(feed)
}