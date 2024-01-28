package com.msarangal.newsapp.data

import com.msarangal.newsapp.data.remote.NewsApiService
import com.msarangal.newsapp.data.remote.model.NewsResponse
import com.msarangal.newsapp.util.Constants.API_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    val newsApiService: NewsApiService
) : NewsRepository {

    override fun getBreakingNews(): Flow<NewsResponse> = flow {
        emit(newsApiService.getBreakingNews(API_KEY))
    }

    override fun getBreakingNewsForCategory(category: String): Flow<NewsResponse> = flow {
        val queryMap = mapOf("category" to category, "apiKey" to API_KEY)
        emit(newsApiService.getBreakingNewsForCategory(queryMap))
    }
}