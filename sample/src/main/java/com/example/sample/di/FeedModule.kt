package com.example.sample.di

import com.example.sample.xml.Feed
import com.example.sample.xml.FeedListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeedModule {

    @Provides
    @Singleton
    fun provideFeed(): FeedListener {
        return Feed
    }
}