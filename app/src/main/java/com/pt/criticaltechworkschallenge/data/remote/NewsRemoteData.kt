package com.pt.criticaltechworkschallenge.data.remote

import com.pt.criticaltechworkschallenge.data.remote.dto.NewsApiResponseDto
import retrofit2.Response

interface NewsRemoteData {
    suspend fun getTopHeadlines(): Response<NewsApiResponseDto>
}
