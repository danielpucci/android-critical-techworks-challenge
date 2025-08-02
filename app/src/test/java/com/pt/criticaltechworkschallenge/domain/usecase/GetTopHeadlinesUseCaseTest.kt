package com.pt.criticaltechworkschallenge.domain.usecase

import com.pt.criticaltechworkschallenge.domain.model.Article
import com.pt.criticaltechworkschallenge.domain.repository.NewsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetTopHeadlinesUseCaseTest {

    private lateinit var newsRepository: NewsRepository
    private lateinit var getTopHeadlinesUseCase: GetTopHeadlinesUseCase

    @Before
    fun setUp() {
        newsRepository = mockk()
        getTopHeadlinesUseCase = GetTopHeadlinesUseCase(newsRepository)
    }

    @Test
    fun `invoke should call getTopHeadlines on repository and return the article flow`() = runTest {
        val fakeArticleList = listOf(
            Article(url = "url1", sourceName = "Source 1", author = "Author 1", title = "Title 1", description = "Desc 1", urlToImage = null, publishedAt = "date1", content = "Content 1"),
            Article(url = "url2", sourceName = "Source 2", author = "Author 2", title = "Title 2", description = "Desc 2", urlToImage = null, publishedAt = "date2", content = "Content 2")
        )

        coEvery { newsRepository.getTopHeadlines() } returns flowOf(fakeArticleList)

        val resultFlow = getTopHeadlinesUseCase()

        val resultList = resultFlow.first()

        assertEquals(fakeArticleList, resultList)
        assertEquals(2, resultList.size)
        assertEquals("Title 1", resultList[0].title)
    }
}