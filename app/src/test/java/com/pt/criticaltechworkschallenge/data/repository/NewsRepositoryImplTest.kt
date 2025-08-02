package com.pt.criticaltechworkschallenge.data.repository

import com.pt.criticaltechworkschallenge.data.local.ArticleDao
import com.pt.criticaltechworkschallenge.data.remote.NewsRemoteData
import com.pt.criticaltechworkschallenge.data.remote.dto.ArticleDto
import com.pt.criticaltechworkschallenge.data.remote.dto.NewsApiResponseDto
import com.pt.criticaltechworkschallenge.data.remote.dto.SourceDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class NewsRepositoryImplTest {

    private lateinit var remoteData: NewsRemoteData
    private lateinit var articleDao: ArticleDao

    private lateinit var repository: NewsRepositoryImpl

    @Before
    fun setUp() {
        remoteData = mockk()
        articleDao = mockk()
        repository = NewsRepositoryImpl(remoteData, articleDao)
    }

    @Test
    fun `refreshTopHeadlines on success should clear DAO, fetch from network and insert new articles`() = runTest {
        val fakeDto = ArticleDto(SourceDto("id", "name"), "author", "title", "desc", "url", "img", "date", "content")
        val fakeApiResponse = NewsApiResponseDto("ok", 1, listOf(fakeDto))
        val successfulResponse = Response.success(fakeApiResponse)

        coEvery { remoteData.getTopHeadlines(any()) } returns successfulResponse
        coEvery { articleDao.insertOrUpdateArticles(any()) } just runs

        repository.refreshTopHeadlines("techcrunch")

        coVerify(exactly = 1) { articleDao.deleteAllArticles() }
        coVerify(exactly = 1) { remoteData.getTopHeadlines("techcrunch") }
        coVerify(exactly = 1) { articleDao.insertOrUpdateArticles(any()) }
    }

    @Test
    fun `refreshTopHeadlines with unsuccessful network response should throw exception`() = runTest {
        val errorResponse = Response.error<NewsApiResponseDto>(404, "".toResponseBody(null))
        coEvery { remoteData.getTopHeadlines(any()) } returns errorResponse

        try {
            repository.refreshTopHeadlines("techcrunch")
            fail("Expected an exception to be thrown, but it wasn't.")
        } catch (e: Exception) {

            val message = e.message
            assertTrue(message != null && message.contains("Request failed with code. Code: 404"))
        }

        coVerify(exactly = 0) { articleDao.insertOrUpdateArticles(any()) }
        coVerify(exactly = 0) { articleDao.deleteAllArticles() }
    }
}