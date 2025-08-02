package com.pt.criticaltechworkschallenge.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pt.criticaltechworkschallenge.domain.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles ORDER BY publishedAt DESC")
    fun getAllArticles(): Flow<List<Article>>

    @Query("SELECT * FROM articles WHERE url = :url")
    fun getArticleByUrl(url: String): Flow<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateArticles(articles: List<Article>)

    @Query("DELETE FROM articles")
    suspend fun deleteAllArticles()
}