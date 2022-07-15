package com.example.sample.domain.use_case

import com.example.sample.data.model.Article
import com.example.sample.data.model.Feed
import com.example.sample.domain.repo.ArticleRepository
import com.example.sample.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(private val articleRepository: ArticleRepository) {

    operator fun invoke(feed: Feed): Flow<Result<List<Article>>> = flow {
        try {
            val getArticle = articleRepository.getArticles(feed)
            emit(Result.Success(getArticle))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}