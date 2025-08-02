package com.pt.criticaltechworkschallenge.data.mapper

import com.pt.criticaltechworkschallenge.data.remote.dto.ArticleDto
import com.pt.criticaltechworkschallenge.domain.model.Article


fun ArticleDto.toDomain(): Article {
    return Article(
        sourceName = this.source.toDomain().name,
        author = this.author,
        title = this.title,
        description = this.description ?: "",
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content ?: ""
    )
}