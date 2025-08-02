package com.pt.criticaltechworkschallenge.ui.news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pt.criticaltechworkschallenge.data.preferences.UserPreferencesRepository
import com.pt.criticaltechworkschallenge.domain.usecase.GetTopHeadlinesUseCase
import com.pt.criticaltechworkschallenge.domain.usecase.RefreshTopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val refreshTopHeadlinesUseCase: RefreshTopHeadlinesUseCase,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()
    private var currentSourceId: String? = null

    init {
        observeArticles()
        observeUserPreferences()
    }

    private fun observeUserPreferences() {
        viewModelScope.launch {
            userPreferencesRepository.selectedSource.collect { (sourceId, sourceName) ->
                currentSourceId = sourceId
                _uiState.update { it.copy(sourceName = sourceName) }
                refreshHeadlines()
            }
        }
    }

    fun onSourceSelected(sourceId: String, sourceName: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveSourcePreference(sourceId, sourceName)
        }
    }

    private fun observeArticles() {
        viewModelScope.launch {
            getTopHeadlinesUseCase().collect { articles ->
                _uiState.update { it.copy(articles = articles) }
            }
        }
    }

    fun refreshHeadlines() {
        currentSourceId?.let { sourceId ->
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true) }
                try {
                    refreshTopHeadlinesUseCase(sourceId)
                } catch (e: Exception) {
                    _uiState.update { it.copy(errorMessage = e.message ?: "Unknown error") }
                } finally {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun onErrorMessageShown() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}