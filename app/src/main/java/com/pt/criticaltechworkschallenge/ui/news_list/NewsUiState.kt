package com.pt.criticaltechworkschallenge.ui.news_list

import com.pt.criticaltechworkschallenge.domain.model.Article

data class NewsUiState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)