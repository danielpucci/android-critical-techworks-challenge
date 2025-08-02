package com.pt.criticaltechworkschallenge.domain.usecase

import com.pt.criticaltechworkschallenge.domain.repository.NewsRepository
import io.mockk.coVerify
import io.mockk.just
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RefreshTopHeadlinesUseCaseTest {

    private lateinit var newsRepository: NewsRepository
    private lateinit var refreshTopHeadlinesUseCase: RefreshTopHeadlinesUseCase

    @Before
    fun setUp() {
        newsRepository = mockk()
        refreshTopHeadlinesUseCase = RefreshTopHeadlinesUseCase(newsRepository)
    }

    @Test
    fun `invoke should call refreshTopHeadlines on repository`() = runTest {
        val sourceId = "techcrunch"
        coEvery { newsRepository.refreshTopHeadlines(any()) } just runs

        refreshTopHeadlinesUseCase(sourceId)

        coVerify(exactly = 1) { newsRepository.refreshTopHeadlines(sourceId) }
    }
}