package com.pt.criticaltechworkschallenge.di

import com.pt.criticaltechworkschallenge.data.api.NewsApiService
import com.pt.criticaltechworkschallenge.data.remote.NewsRemoteData
import com.pt.criticaltechworkschallenge.data.remote.NewsRemoteDataImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {
    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(newsApiService: NewsApiService): NewsRemoteData {
        return NewsRemoteDataImpl(newsApiService)
    }
}