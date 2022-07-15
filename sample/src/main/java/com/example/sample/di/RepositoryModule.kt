package com.example.sample.di


import com.example.sample.data.repo.ArticleRepositoryImpl
import com.example.sample.data.source.remote.ArticleRemoteDataSource
import com.example.sample.data.source.remote.ArticleRemoteDataSourceImpl
import com.example.sample.domain.repo.ArticleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindArticleRepository(articleRepositoryImpl: ArticleRepositoryImpl): ArticleRepository

    @Singleton
    @Binds
    abstract fun bindArticleRemoteDataSource(articleRemoteDataSourceImpl: ArticleRemoteDataSourceImpl): ArticleRemoteDataSource

}

