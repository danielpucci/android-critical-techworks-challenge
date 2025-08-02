package com.pt.criticaltechworkschallenge.data.remote

import com.pt.criticaltechworkschallenge.data.api.NewsApiService
import com.pt.criticaltechworkschallenge.data.remote.dto.NewsApiResponseDto
import retrofit2.Response
import javax.inject.Inject

class NewsRemoteDataImpl @Inject constructor(
    private val apiService: NewsApiService
) : NewsRemoteData {
    override suspend fun getTopHeadlines(sourceId: String?): Response<NewsApiResponseDto> {
        return apiService.getTopHeadlines(sourceId)
    }
}
