package com.msarangal.newsapp.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msarangal.newsapp.data.remote.NetworkHelper
import com.msarangal.newsapp.data.remote.NetworkManager
import com.msarangal.newsapp.data.remote.NewsApi
import com.msarangal.newsapp.data.remote.model.NewsResponse
import com.msarangal.newsapp.domain.NewsRepository
import com.msarangal.newsapp.navigation.NewsSearch
import com.msarangal.newsapp.util.Constants.CATEGORY_ENTERTAINMENT
import com.msarangal.newsapp.util.Constants.CATEGORY_HEALTH
import com.msarangal.newsapp.util.Constants.CATEGORY_SPORTS
import com.msarangal.newsapp.util.Constants.CATEGORY_TECHNOLOGY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val networkManager: NetworkManager,
    private val networkHelper: NetworkHelper,
    private val newsRepository: NewsRepository,
    private val newsApi: NewsApi,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val categoryMap = mapOf(
        CATEGORY_HEALTH to 0,
        CATEGORY_SPORTS to 1,
        CATEGORY_TECHNOLOGY to 2,
        CATEGORY_ENTERTAINMENT to 3
    )

    val searchQuery =
        savedStateHandle.getStateFlow(key = NewsSearch.SEARCH_QUERY_ARG, initialValue = "")

    var initialCategoryIndex = categoryMap.values.first()

    val breakingNewsStateFlow = flow {
        if (networkHelper.isConnected()) {
            emit(BreakingNewsUiState.Loading)
            val result = newsRepository.getBreakingNews()
            if (result.isSuccessful) {
                result.body()?.let { newsResponse ->
                    emit(BreakingNewsUiState.Success(data = newsResponse))
                }
            } else {
                emit(BreakingNewsUiState.Failure(error = "Something went wrong"))
            }
        } else {
            emit(BreakingNewsUiState.Failure(error = "No Internet"))
        }

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        BreakingNewsUiState.UnInitialized
    )

    val healthNewsStateFlow = flow {
        if (networkHelper.isConnected()) {
            emit(HealthNewsUiState.Loading)
            val result = newsRepository.getBreakingNewsForCategory(CATEGORY_HEALTH)
            if (result.isSuccessful) {
                result.body()?.let { newsResponse ->
                    emit(HealthNewsUiState.Success(data = newsResponse))
                }
            } else {
                emit(HealthNewsUiState.Failure(error = "Something went wrong"))
            }
        } else {
            emit(HealthNewsUiState.Failure(error = "No Internet"))
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        HealthNewsUiState.UnInitialized
    )

    val sportsNewsStateFlow = flow {
        if (networkHelper.isConnected()) {
            emit(SportsNewsUiState.Loading)
            val result = newsRepository.getBreakingNewsForCategory(CATEGORY_SPORTS)
            if (result.isSuccessful) {
                result.body()?.let { newsResponse ->
                    emit(SportsNewsUiState.Success(data = newsResponse))
                }
            } else {
                emit(SportsNewsUiState.Failure(error = "Something went wrong"))
            }
        } else {
            emit(SportsNewsUiState.Failure(error = "No Internet"))
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        SportsNewsUiState.UnInitialized
    )

    val techNewsStateFlow = flow {
        if (networkHelper.isConnected()) {
            emit(TechNewsUiState.Loading)
            val result = newsRepository.getBreakingNewsForCategory(CATEGORY_TECHNOLOGY)
            if (result.isSuccessful) {
                result.body()?.let { newsResponse ->
                    emit(TechNewsUiState.Success(data = newsResponse))
                }
            } else {
                emit(TechNewsUiState.Failure(error = "Something went wrong"))
            }
        } else {
            emit(TechNewsUiState.Failure(error = "No Internet"))
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        TechNewsUiState.UnInitialized
    )

    val entertainmentNewsStateFlow = flow {
        if (networkHelper.isConnected()) {
            emit(EntertainmentNewsUiState.Loading)
            val result = newsRepository.getBreakingNewsForCategory(CATEGORY_ENTERTAINMENT)
            if (result.isSuccessful) {
                result.body()?.let { newsResponse ->
                    emit(EntertainmentNewsUiState.Success(data = newsResponse))
                }
            } else {
                emit(EntertainmentNewsUiState.Failure(error = "Something went wrong"))
            }
        } else {
            emit(EntertainmentNewsUiState.Failure(error = "No Internet"))
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        EntertainmentNewsUiState.UnInitialized
    )

    init {
        // Should always keep init light weight.
        // TODO: Remove initialization of calls from here
        /**
         * 1.Difficult to test viewmodel in isolation: Because the moment ViewModel is created
         * a network call is fired. This complicates ViewModel testing.
         * 2. A Configuration change may lead to re-fetching of data or unexpected behaviour
         * 3.
         */
//        viewModelScope.launch(Dispatchers.IO) {
//            val result = newsApi.getBreakingNewsOnMain(BuildConfig.API_KEY)
//        }
        //fetchBreakingNews()
        //fetchBreakingNewsUsingLiveData()
        //fetchHealthNews()
//        fetchSportsNews()
//        fetchTechNews()
//        fetchPoliticsNews()
    }

//    private fun fetchBreakingNews() {
//        viewModelScope.launch {
//            if (networkHelper.isConnected()) {
//                _breakingNewsStateFlow.value = BreakingNewsUiState.Loading
//                newsRepository.getBreakingNews()
//                    .flowOn(Dispatchers.IO)
//                    .catch {
//                        _breakingNewsStateFlow.value = BreakingNewsUiState.Failure(
//                            error = it.localizedMessage ?: "Exception occurred"
//                        )
//                    }
//                    .collectLatest {
//                        it.body()?.let { newsResponse ->
//                            _breakingNewsStateFlow.value =
//                                BreakingNewsUiState.Success(data = newsResponse)
//                        }
//                    }
//            } else {
//                _breakingNewsStateFlow.value =
//                    BreakingNewsUiState.Failure(
//                        error = "No Internet"
//                    )
//            }
//        }
//    }

    private fun fetchBreakingNewsUsingLiveData() {
        viewModelScope.launch {
            newsRepository.getBreakingNewsWithLiveData()
        }
    }

    fun onSearchQueryChanged(query: String) {

    }

    fun onSearchTriggered(query: String) {

    }
}

sealed class BreakingNewsUiState {
    data class Success(val data: NewsResponse) : BreakingNewsUiState()
    data class Failure(val error: String) : BreakingNewsUiState()
    object Loading : BreakingNewsUiState()
    object UnInitialized : BreakingNewsUiState()
}

sealed class HealthNewsUiState {
    data class Success(val data: NewsResponse) : HealthNewsUiState()
    data class Failure(val error: String) : HealthNewsUiState()
    object Loading : HealthNewsUiState()
    object UnInitialized : HealthNewsUiState()
}

sealed class SportsNewsUiState {
    data class Success(val data: NewsResponse) : SportsNewsUiState()
    data class Failure(val error: String) : SportsNewsUiState()
    object Loading : SportsNewsUiState()
    object UnInitialized : SportsNewsUiState()
}

sealed class TechNewsUiState {
    data class Success(val data: NewsResponse) : TechNewsUiState()
    data class Failure(val error: String) : TechNewsUiState()
    object Loading : TechNewsUiState()
    object UnInitialized : TechNewsUiState()
}

sealed class EntertainmentNewsUiState {
    data class Success(val data: NewsResponse) : EntertainmentNewsUiState()
    data class Failure(val error: String) : EntertainmentNewsUiState()
    object Loading : EntertainmentNewsUiState()
    object UnInitialized : EntertainmentNewsUiState()
}