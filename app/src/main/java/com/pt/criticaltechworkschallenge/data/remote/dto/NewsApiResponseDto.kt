package com.pt.criticaltechworkschallenge.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsApiResponseDto(
    @field:Json(name = "status") val status: String,
    @field:Json(name = "totalResults") val totalResults: Int,
    @field:Json(name = "articles") val articleResponse: List<ArticleDto>
)
