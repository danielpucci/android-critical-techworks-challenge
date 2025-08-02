package com.pt.criticaltechworkschallenge.data.api

import com.pt.criticaltechworkschallenge.data.remote.dto.NewsApiResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(): Response<NewsApiResponseDto>
}