package com.example.sample.data.source.remote

import com.example.sample.data.model.Article
import com.example.sample.data.model.Feed
import com.example.sample.util.Result
import com.example.sample.xml.FeedListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticleRemoteDataSourceImpl @Inject constructor(private val feedListener: FeedListener) :
    ArticleRemoteDataSource {

    override fun getArticles(feed: Feed): Flow<List<Article>> = flow {
        emit(feedListener.getArticles(feed))
    }

}