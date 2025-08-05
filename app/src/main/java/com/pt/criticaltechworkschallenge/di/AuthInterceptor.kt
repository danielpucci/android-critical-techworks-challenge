package com.pt.criticaltechworkschallenge.di

import com.pt.criticaltechworkschallenge.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val url = originalUrl.newBuilder()
            .build()

        val request = originalRequest.newBuilder()
            .addHeader("X-Api-Key", BuildConfig.API_KEY)
            .url(url)
            .build()

        return chain.proceed(request)
    }
}