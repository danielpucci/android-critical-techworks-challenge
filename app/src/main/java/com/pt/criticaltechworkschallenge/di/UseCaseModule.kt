package com.pt.criticaltechworkschallenge.di

import com.pt.criticaltechworkschallenge.domain.repository.NewsRepository
import com.pt.criticaltechworkschallenge.domain.usecase.GetArticleByUrlUseCase
import com.pt.criticaltechworkschallenge.domain.usecase.GetTopHeadlinesUseCase
import com.pt.criticaltechworkschallenge.domain.usecase.RefreshTopHeadlinesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetTopHeadlinesUseCase(repository: NewsRepository): GetTopHeadlinesUseCase {
        return GetTopHeadlinesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRefreshTopHeadlinesUseCase(repository: NewsRepository): RefreshTopHeadlinesUseCase {
        return RefreshTopHeadlinesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetArticleByUrlUseCase(repository: NewsRepository): GetArticleByUrlUseCase {
        return GetArticleByUrlUseCase(repository)
    }
}