package com.pt.criticaltechworkschallenge.domain.usecase

import com.pt.criticaltechworkschallenge.domain.model.Article
import com.pt.criticaltechworkschallenge.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(private val repository: NewsRepository) {
    operator fun invoke(): Flow<List<Article>> = repository.getTopHeadlines()
}