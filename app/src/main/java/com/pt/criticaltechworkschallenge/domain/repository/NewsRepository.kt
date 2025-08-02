package com.pt.criticaltechworkschallenge.domain.repository

import com.pt.criticaltechworkschallenge.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getTopHeadlines(): Flow<List<Article>>
    fun getArticleByUrl(url: String): Flow<Article>
    suspend fun refreshTopHeadlines()
}
