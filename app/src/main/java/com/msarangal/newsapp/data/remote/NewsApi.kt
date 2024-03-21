package com.msarangal.newsapp.data.remote

import com.msarangal.newsapp.data.remote.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface NewsApi {

    @GET("/v2/top-headlines?language=en")
    suspend fun getBreakingNews(@Query("apiKey") key: String): Response<NewsResponse>

    @GET("/v2/top-headlines?language=en")
    fun getBreakingNewsOnMain(@Query("apiKey") key: String): Response<NewsResponse>

    @GET("/v2/top-headlines?language=en")
    suspend fun getBreakingNewsForCategory(@QueryMap options: Map<String, String>): Response<NewsResponse>

    @GET("/v2/everything")
    suspend fun getNewsSearchResults(@QueryMap options: Map<String, String>): NewsResponse
}