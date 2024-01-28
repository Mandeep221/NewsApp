package com.msarangal.newsapp.data

import com.msarangal.newsapp.data.remote.model.NewsResponse
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getBreakingNews(): Flow<NewsResponse>
    fun getBreakingNewsForCategory(category: String): Flow<NewsResponse>
}