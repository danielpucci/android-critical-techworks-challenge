package com.pt.criticaltechworkschallenge.ui.news_list

import app.cash.turbine.test
import com.pt.criticaltechworkschallenge.data.preferences.UserPreferencesRepository
import com.pt.criticaltechworkschallenge.domain.model.Article
import com.pt.criticaltechworkschallenge.domain.usecase.GetTopHeadlinesUseCase
import com.pt.criticaltechworkschallenge.domain.usecase.RefreshTopHeadlinesUseCase
import com.pt.criticaltechworkschallenge.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var getTopHeadlinesUseCase: GetTopHeadlinesUseCase
    private lateinit var refreshTopHeadlinesUseCase: RefreshTopHeadlinesUseCase
    private lateinit var userPreferencesRepository: UserPreferencesRepository
    private lateinit var viewModel: NewsViewModel

    private val fakeArticle = Article(
        "url",
        "TechCrunch",
        "Author",
        "Title",
        "Desc",
        "img",
        "date",
        "content"
    )

    @Before
    fun setUp() {
        getTopHeadlinesUseCase = mockk()
        refreshTopHeadlinesUseCase = mockk()
        userPreferencesRepository = mockk()

        coEvery { refreshTopHeadlinesUseCase(any()) } returns Unit
        coEvery { userPreferencesRepository.selectedSource } returns flowOf("techcrunch" to "TechCrunch")
    }

    @Test
    fun `when ViewModel is initialized, it fetches articles and updates state successfully`() = runTest {
        val fakeArticles = listOf(fakeArticle)
        coEvery { getTopHeadlinesUseCase() } returns flowOf(fakeArticles)

        viewModel = NewsViewModel(getTopHeadlinesUseCase, refreshTopHeadlinesUseCase, userPreferencesRepository)

        viewModel.uiState.test {
            assertEquals(emptyList<Article>(), awaitItem().articles)

            mainDispatcherRule.testDispatcher.scheduler.runCurrent()

            val emissionWithData = awaitItem()
            assertEquals(fakeArticles, emissionWithData.articles)
            assertEquals(false, emissionWithData.isLoading)
            assertNull(emissionWithData.errorMessage)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when refresh fails, UI state is updated with an error message`() = runTest {
        val errorMessage = "Network Error"
        coEvery { getTopHeadlinesUseCase() } returns flowOf(emptyList())
        coEvery { refreshTopHeadlinesUseCase(any()) } throws Exception(errorMessage)

        viewModel = NewsViewModel(getTopHeadlinesUseCase, refreshTopHeadlinesUseCase, userPreferencesRepository)

        viewModel.uiState.test {
            mainDispatcherRule.testDispatcher.scheduler.runCurrent()

            val finalState = expectMostRecentItem()

            assertEquals(errorMessage, finalState.errorMessage)
            assertEquals(false, finalState.isLoading)
        }
    }

    @Test
    fun `when a new source is selected, preference is saved and refresh is called`() = runTest {
        coEvery { getTopHeadlinesUseCase() } returns flowOf(emptyList())
        coEvery { userPreferencesRepository.saveSourcePreference(any(), any()) } returns Unit

        val sourceFlow = kotlinx.coroutines.flow.MutableSharedFlow<Pair<String, String>>(replay = 1)
        coEvery { userPreferencesRepository.selectedSource } returns sourceFlow
        sourceFlow.emit("techcrunch" to "TechCrunch")

        viewModel = NewsViewModel(getTopHeadlinesUseCase, refreshTopHeadlinesUseCase, userPreferencesRepository)

        mainDispatcherRule.testDispatcher.scheduler.runCurrent()

        viewModel.onSourceSelected("bloomberg", "Bloomberg")
        sourceFlow.emit("bloomberg" to "Bloomberg")

        mainDispatcherRule.testDispatcher.scheduler.runCurrent()

        coVerify(exactly = 1) { userPreferencesRepository.saveSourcePreference("bloomberg", "Bloomberg") }
        coVerify(exactly = 2) { refreshTopHeadlinesUseCase(any()) }
    }
}