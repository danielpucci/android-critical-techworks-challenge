package com.pt.criticaltechworkschallenge.domain.usecase

import com.pt.criticaltechworkschallenge.domain.repository.NewsRepository
import javax.inject.Inject

class RefreshTopHeadlinesUseCase @Inject constructor(private val repository: NewsRepository) {
    suspend operator fun invoke() = repository.refreshTopHeadlines()
}