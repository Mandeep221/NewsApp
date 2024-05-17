package com.msarangal.newsapp.domain

import androidx.lifecycle.LiveData
import com.msarangal.newsapp.data.remote.NetworkResult
import com.msarangal.newsapp.data.remote.model.NewsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Response

interface NewsRepository {
    val stateFlowBreakingNews: StateFlow<NetworkResult<NewsResponse>>
    val breakingNewsLiveData: LiveData<NetworkResult<NewsResponse>>
    suspend fun getBreakingNews(): Response<NewsResponse>
    suspend fun getBreakingNewsForCategory(category: String): Response<NewsResponse>

    suspend fun getBreakingNewsWithLiveData()
}