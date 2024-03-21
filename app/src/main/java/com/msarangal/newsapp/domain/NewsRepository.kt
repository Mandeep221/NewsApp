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
    fun getBreakingNews(): Flow<NetworkResult<NewsResponse>>
    fun getBreakingNewsForCategory(category: String): Flow<Response<NewsResponse>>

    suspend fun getBreakingNewsWithLiveData()
}