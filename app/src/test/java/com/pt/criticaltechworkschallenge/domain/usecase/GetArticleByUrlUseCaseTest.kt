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

class GetArticleByUrlUseCaseTest {

    private lateinit var newsRepository: NewsRepository
    private lateinit var getArticleByUrlUseCase: GetArticleByUrlUseCase

    @Before
    fun setUp() {
        newsRepository = mockk()
        getArticleByUrlUseCase = GetArticleByUrlUseCase(newsRepository)
    }

    @Test
    fun `invoke should call getArticleByUrl on repository and return the data`() = runTest {
        val fakeUrl = "http://example.com"
        val fakeArticle = Article(url = fakeUrl, sourceName = "", author = "", title = "", description = "", urlToImage = null, publishedAt = "", content = "")
        coEvery { newsRepository.getArticleByUrl(fakeUrl) } returns flowOf(fakeArticle)

        val resultFlow = getArticleByUrlUseCase(fakeUrl)

        val resultArticle = resultFlow.first()
        assertEquals(fakeArticle, resultArticle)
    }
}