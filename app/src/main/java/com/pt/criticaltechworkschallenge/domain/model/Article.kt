package com.pt.criticaltechworkschallenge.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey
    val url: String,
    val sourceName: String,
    val author: String?,
    val title: String,
    val description: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String
)