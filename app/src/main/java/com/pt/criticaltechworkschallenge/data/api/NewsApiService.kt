package com.pt.criticaltechworkschallenge.data.api

import com.pt.criticaltechworkschallenge.data.remote.dto.NewsApiResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("sources") sources: String?): Response<NewsApiResponseDto>
}