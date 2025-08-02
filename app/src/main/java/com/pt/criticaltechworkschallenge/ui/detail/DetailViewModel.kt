package com.pt.criticaltechworkschallenge.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pt.criticaltechworkschallenge.domain.model.Article
import com.pt.criticaltechworkschallenge.domain.usecase.GetArticleByUrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getArticleByUrlUseCase: GetArticleByUrlUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<Article?>(null)
    val uiState = _uiState.asStateFlow()

    init {
        val encodedUrl = savedStateHandle.get<String>("articleUrl") ?: ""
        if (encodedUrl.isNotBlank()) {
            viewModelScope.launch {
                val decodedUrl = URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.toString())
                getArticleByUrlUseCase(decodedUrl).collectLatest { article ->
                    _uiState.value = article
                }
            }
        }
    }
}