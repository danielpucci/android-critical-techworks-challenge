package com.pt.criticaltechworkschallenge.di

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val url = originalUrl.newBuilder()
            .addQueryParameter("sources", "bbc-news")
            .build()

        val request = originalRequest.newBuilder()
            .addHeader("X-Api-Key", "2e1a6d5058ee4b88824194b40d1d5df6")
            .url(url)
            .build()

        return chain.proceed(request)
    }
}