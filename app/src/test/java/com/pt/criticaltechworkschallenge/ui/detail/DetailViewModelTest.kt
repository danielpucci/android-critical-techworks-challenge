package com.pt.criticaltechworkschallenge.ui.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.pt.criticaltechworkschallenge.domain.model.Article
import com.pt.criticaltechworkschallenge.domain.usecase.GetArticleByUrlUseCase
import com.pt.criticaltechworkschallenge.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var getArticleByUrlUseCase: GetArticleByUrlUseCase
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: DetailViewModel

    private val fakeUrl = "https://example.com/article1"
    private val fakeArticle = Article(
        url = fakeUrl,
        sourceName = "Test Source",
        author = "John Doe",
        title = "Test Title",
        description = "Test Description",
        urlToImage = "https://example.com/image.jpg",
        publishedAt = "2025-08-02T16:00:00Z",
        content = "Test content."
    )

    @Test
    fun `when initialized with a valid URL, ViewModel loads the article successfully`() = runTest {
        val encodedUrl = URLEncoder.encode(fakeUrl, StandardCharsets.UTF_8.toString())
        savedStateHandle = mockk()
        every { savedStateHandle.get<String>("articleUrl") } returns encodedUrl

        getArticleByUrlUseCase = mockk()
        coEvery { getArticleByUrlUseCase(fakeUrl) } returns flowOf(fakeArticle)

        viewModel = DetailViewModel(getArticleByUrlUseCase, savedStateHandle)

        viewModel.uiState.test {
            assertEquals(null, awaitItem())

            mainDispatcherRule.testDispatcher.scheduler.runCurrent()

            val emissionWithData = awaitItem()
            assertEquals(fakeArticle, emissionWithData)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when use case does not find the article, UI state remains null`() = runTest {
        val encodedUrl = URLEncoder.encode(fakeUrl, StandardCharsets.UTF_8.toString())
        savedStateHandle = mockk()
        every { savedStateHandle.get<String>("articleUrl") } returns encodedUrl

        getArticleByUrlUseCase = mockk()
        coEvery { getArticleByUrlUseCase(fakeUrl) } returns flowOf<Article?>(null)

        viewModel = DetailViewModel(getArticleByUrlUseCase, savedStateHandle)

        viewModel.uiState.test {
            val emission = awaitItem()
            assertNull(emission)
        }
    }
}