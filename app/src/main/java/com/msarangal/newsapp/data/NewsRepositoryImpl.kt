package com.msarangal.newsapp.data

import com.msarangal.newsapp.BuildConfig
import com.msarangal.newsapp.data.remote.NewsApiService
import com.msarangal.newsapp.data.remote.model.NewsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    val newsApiService: NewsApiService
) : NewsRepository {

    override fun getBreakingNews(): Flow<NewsResponse> = flow {
        emit(newsApiService.getBreakingNews(BuildConfig.API_KEY))
    }

    override fun getBreakingNewsForCategory(category: String): Flow<NewsResponse> = flow {
        val queryMap = mapOf("category" to category, "apiKey" to BuildConfig.API_KEY)
        emit(newsApiService.getBreakingNewsForCategory(queryMap))
    }
}