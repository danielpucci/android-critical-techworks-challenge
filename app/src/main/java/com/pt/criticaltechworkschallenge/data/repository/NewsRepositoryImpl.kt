package com.pt.criticaltechworkschallenge.data.repository

import com.pt.criticaltechworkschallenge.data.local.ArticleDao
import com.pt.criticaltechworkschallenge.data.mapper.toDomain
import com.pt.criticaltechworkschallenge.data.remote.NewsRemoteData
import com.pt.criticaltechworkschallenge.domain.model.Article
import com.pt.criticaltechworkschallenge.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val remoteData: NewsRemoteData,
    private val articleDao: ArticleDao
) : NewsRepository {
    override suspend fun refreshTopHeadlines(sourceId: String) {

        val response = remoteData.getTopHeadlines(sourceId)
        if (response.isSuccessful && response.body() != null) {
            val domainArticles = response.body()!!.articleResponse.map { it.toDomain() }
            articleDao.deleteAllArticles()
            articleDao.insertOrUpdateArticles(domainArticles)
        } else {
            throw Exception("Request failed with code. Code: ${response.code()}")
        }
    }

    override fun getTopHeadlines(): Flow<List<Article>> {
        return articleDao.getAllArticles()
    }

    override fun getArticleByUrl(url: String): Flow<Article?> {
        return articleDao.getArticleByUrl(url)
    }
}