package com.example.sample.data.source.remote

import com.example.sample.data.model.Article
import com.example.sample.data.model.Feed
import com.example.sample.util.Result
import kotlinx.coroutines.flow.Flow

interface ArticleRemoteDataSource {
    fun getArticles(feed: Feed): Flow<List<Article>>
}