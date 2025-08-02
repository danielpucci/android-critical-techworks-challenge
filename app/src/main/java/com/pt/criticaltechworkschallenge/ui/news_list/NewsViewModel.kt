package com.pt.criticaltechworkschallenge.ui.news_list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pt.criticaltechworkschallenge.R
import com.pt.criticaltechworkschallenge.domain.usecase.GetTopHeadlinesUseCase
import com.pt.criticaltechworkschallenge.domain.usecase.RefreshTopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val app: Application,
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val refreshTopHeadlinesUseCase: RefreshTopHeadlinesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    init {
        observeArticles()
        refreshHeadlines()
    }

    private fun observeArticles() {
        viewModelScope.launch {
            getTopHeadlinesUseCase().collect { articles ->
                _uiState.update { it.copy(articles = articles) }
            }
        }
    }

    fun refreshHeadlines() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                refreshTopHeadlinesUseCase()
            } catch (e: IOException) {
                val errorMessage = app.getString(R.string.error_network_response_failed, e.message)
                _uiState.update { it.copy(errorMessage = errorMessage) }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message ?: app.getString(R.string.error_unexpected)) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onErrorMessageShown() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}