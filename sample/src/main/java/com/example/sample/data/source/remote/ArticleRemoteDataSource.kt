package com.example.sample.data.source.remote

import com.example.sample.data.model.Article
import com.example.sample.data.model.Feed
import com.example.sample.util.Result

interface ArticleRemoteDataSource {
    suspend fun getArticles(feed : Feed) : List<Article>
}