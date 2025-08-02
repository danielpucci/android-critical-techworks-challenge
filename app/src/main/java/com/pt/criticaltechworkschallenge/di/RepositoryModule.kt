package com.pt.criticaltechworkschallenge.di

import com.pt.criticaltechworkschallenge.data.local.ArticleDao
import com.pt.criticaltechworkschallenge.data.remote.NewsRemoteData
import com.pt.criticaltechworkschallenge.data.repository.NewsRepositoryImpl
import com.pt.criticaltechworkschallenge.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideNewsRepository(remoteData: NewsRemoteData, articleDao: ArticleDao): NewsRepository {
        return NewsRepositoryImpl(remoteData, articleDao)
    }
}