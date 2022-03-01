package com.example.sample.data.repo

import com.example.sample.data.model.Article
import com.example.sample.data.model.Feed

interface ArticleRepository {
    suspend fun getArticles(feed : Feed) : com.example.sample.util.Result<List<Article>>
}