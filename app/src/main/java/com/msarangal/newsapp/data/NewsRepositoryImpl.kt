package com.msarangal.newsapp.data

import com.msarangal.newsapp.BuildConfig
import com.msarangal.newsapp.data.remote.NewsApi
import com.msarangal.newsapp.data.remote.model.NewsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    val newsApi: NewsApi
) : NewsRepository {

    override fun getBreakingNews(): Flow<NewsResponse> = flow {
        emit(newsApi.getBreakingNews(BuildConfig.API_KEY))
    }

    override fun getBreakingNewsForCategory(category: String): Flow<NewsResponse> = flow {
        val queryMap = mapOf("category" to category, "apiKey" to BuildConfig.API_KEY)
        emit(newsApi.getBreakingNewsForCategory(queryMap))
    }
}