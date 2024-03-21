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


    override fun getBreakingNews(): Flow<NetworkResult<NewsResponse>> = flow {
        //emit(NetworkResponse.Loading())
        if (networkHelper.isConnected()) {
            _breakingNewsLiveData.postValue(NetworkResult.Loading())
            _stateFlowBreakingNews.emit(NetworkResult.Loading())
            try {
                val result = newsApi.getBreakingNews(BuildConfig.API_KEY)
                if (result.isSuccessful && result.body() != null) {
                    //emit(NetworkResponse.Success(response = result.body()))
                    _stateFlowBreakingNews.emit(NetworkResult.Success(response = result.body()))
                    _breakingNewsLiveData.postValue(NetworkResult.Success(response = result.body()))
                } else {
                    emit(NetworkResult.Error(msg = result.message()))
                    _stateFlowBreakingNews.emit(NetworkResult.Error(msg = result.message()))
                    _breakingNewsLiveData.postValue((NetworkResult.Error(msg = result.message())))
                }
            } catch (e: Exception) {
                _stateFlowBreakingNews.emit(NetworkResult.Error(e.toString()))
                _breakingNewsLiveData.postValue((NetworkResult.Error(msg = e.toString())))
            }
        } else {
            _stateFlowBreakingNews.emit(NetworkResult.Error("No Internet"))
            _breakingNewsLiveData.postValue((NetworkResult.Error("No Internet")))
        }
    }

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

    override fun getBreakingNewsForCategory(category: String): Flow<Response<NewsResponse>> {
        return flow {
            val queryMap = mapOf("category" to category, "apiKey" to BuildConfig.API_KEY)
            emit(newsApi.getBreakingNewsForCategory(queryMap))
        }
    }

}