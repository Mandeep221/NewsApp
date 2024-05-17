package com.msarangal.newsapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.msarangal.newsapp.BuildConfig
import com.msarangal.newsapp.data.remote.NetworkHelper
import com.msarangal.newsapp.data.remote.NetworkResult
import com.msarangal.newsapp.data.remote.NewsApi
import com.msarangal.newsapp.data.remote.model.NewsResponse
import com.msarangal.newsapp.domain.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    val newsApi: NewsApi,
    val networkHelper: NetworkHelper
) : NewsRepository {

    private val _stateFlowBreakingNews =
        MutableStateFlow<NetworkResult<NewsResponse>>(value = NetworkResult.Loading())
    override val stateFlowBreakingNews: StateFlow<NetworkResult<NewsResponse>>
        get() = _stateFlowBreakingNews

    private val _breakingNewsLiveData = MutableLiveData<NetworkResult<NewsResponse>>()
    override val breakingNewsLiveData: LiveData<NetworkResult<NewsResponse>>
        get() = _breakingNewsLiveData


    override suspend fun getBreakingNews(): Response<NewsResponse> =
        newsApi.getBreakingNews(BuildConfig.API_KEY)

    override suspend fun getBreakingNewsWithLiveData() {
        if (networkHelper.isConnected()) {
            _breakingNewsLiveData.postValue(NetworkResult.Loading())
            val response = newsApi.getBreakingNews(BuildConfig.API_KEY)
            if (response.isSuccessful && response.body() != null) {
                _breakingNewsLiveData.postValue(NetworkResult.Success(response = response.body()))
            } else {
                _breakingNewsLiveData.postValue(
                    NetworkResult.Error(
                        msg = response.errorBody().toString()
                    )
                )
            }
        } else {
            _breakingNewsLiveData.postValue(
                NetworkResult.Error(
                    msg = "No Internet"
                )
            )
        }
    }

    override suspend fun getBreakingNewsForCategory(category: String): Response<NewsResponse> {
        val queryMap = mapOf("category" to category, "apiKey" to BuildConfig.API_KEY)
        return newsApi.getBreakingNewsForCategory(queryMap)
    }

}