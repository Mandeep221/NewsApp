package com.msarangal.newsapp.ui

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msarangal.newsapp.domain.NewsRepository
import com.msarangal.newsapp.data.remote.NetworkHelper
import com.msarangal.newsapp.data.remote.NetworkManager
import com.msarangal.newsapp.data.remote.NewsApi
import com.msarangal.newsapp.data.remote.NetworkResult
import com.msarangal.newsapp.data.remote.model.NewsResponse
import com.msarangal.newsapp.navigation.NewsSearch
import com.msarangal.newsapp.util.Constants.CATEGORY_HEALTH
import com.msarangal.newsapp.util.Constants.CATEGORY_ENTERTAINMENT
import com.msarangal.newsapp.util.Constants.CATEGORY_SPORTS
import com.msarangal.newsapp.util.Constants.CATEGORY_TECHNOLOGY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
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

    private val _breakingNewsStateFlow =
        MutableStateFlow<BreakingNewsUiState>(BreakingNewsUiState.Loading)

    //val breakingNewsStateFlow = _breakingNewsStateFlow.asStateFlow()
    val breakingNewsStateFlow = newsRepository.stateFlowBreakingNews

    val breakingNewsLiveData get() = newsRepository.breakingNewsLiveData

    private val _healthNewsStateFlow =
        MutableStateFlow<HealthNewsUiState>(HealthNewsUiState.Loading)
    val healthNewsStateFlow = _healthNewsStateFlow.asStateFlow()

    private val _sportsNewsStateFlow =
        MutableStateFlow<SportsNewsUiState>(SportsNewsUiState.Loading)
    val sportsNewsStateFlow = _sportsNewsStateFlow.asStateFlow()

    private val _techNewsStateFlow =
        MutableStateFlow<TechNewsUiState>(TechNewsUiState.Loading)
    val techNewsStateFlow = _techNewsStateFlow.asStateFlow()

    private val _entertainmentNewsStateFlow =
        MutableStateFlow<EntertainmentNewsUiState>(EntertainmentNewsUiState.Loading)
    val entertainmentNewsStateFlow = _entertainmentNewsStateFlow.asStateFlow()

    private val stateLock = Mutex() // For synchronization

    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            val result = newsApi.getBreakingNewsOnMain(BuildConfig.API_KEY)
//        }
        // fetchBreakingNews()
        fetchBreakingNewsUsingLiveData()
        fetchHealthNews()
        fetchSportsNews()
        fetchTechNews()
        fetchPoliticsNews()
    }

    private fun fetchBreakingNews() {
        viewModelScope.launch {
            newsRepository.getBreakingNews()
                .flowOn(Dispatchers.IO)
                .catch {
                    _breakingNewsStateFlow.value =
                        BreakingNewsUiState.Failure(
                            error = it.localizedMessage ?: "Exception occurred"
                        )
                }
                .collectLatest {
                    when (it) {
                        is NetworkResult.Loading -> {
                            _breakingNewsStateFlow.value = BreakingNewsUiState.Loading
                        }

                        is NetworkResult.Error -> {
                            _breakingNewsStateFlow.value = BreakingNewsUiState.Failure(
                                error = it.errorMsg ?: "Something went wrong"
                            )
                        }

                        is NetworkResult.Success -> {
                            it.data?.let { newsResponse ->
                                _breakingNewsStateFlow.value =
                                    BreakingNewsUiState.Success(data = newsResponse)
                            }
                        }
                    }
                }
        }
    }

    private fun fetchBreakingNewsUsingLiveData() {
        viewModelScope.launch {
            newsRepository.getBreakingNewsWithLiveData()
        }
    }

    private fun fetchHealthNews() {
        viewModelScope.launch {
            if (networkHelper.isConnected()) {
                _healthNewsStateFlow.value = HealthNewsUiState.Loading
                newsRepository.getBreakingNewsForCategory(category = CATEGORY_HEALTH)
                    .flowOn(Dispatchers.IO)
                    .catch {
                        _healthNewsStateFlow.value =
                            HealthNewsUiState.Failure(
                                error = it.localizedMessage ?: "Exception occurred"
                            )
                    }
                    .collectLatest {
                        it.body()?.let { newsResponse ->
                            _healthNewsStateFlow.value =
                                HealthNewsUiState.Success(data = newsResponse)
                        }
                    }
            } else {
                _healthNewsStateFlow.value =
                    HealthNewsUiState.Failure(
                        error = "No Internet"
                    )
            }
        }
    }

    private fun fetchSportsNews() {
        viewModelScope.launch {
            if (networkHelper.isConnected()) {
                _sportsNewsStateFlow.value = SportsNewsUiState.Loading
                newsRepository.getBreakingNewsForCategory(category = CATEGORY_SPORTS)
                    .flowOn(Dispatchers.IO)
                    .catch {
                        _sportsNewsStateFlow.value =
                            SportsNewsUiState.Failure(
                                error = it.localizedMessage ?: "Exception occurred"
                            )
                    }
                    .collectLatest {
                        it.body()?.let { newsResponse ->
                            _sportsNewsStateFlow.value =
                                SportsNewsUiState.Success(data = newsResponse)
                        }
                    }
            } else {
                _sportsNewsStateFlow.value =
                    SportsNewsUiState.Failure(
                        error = "No Internet"
                    )
            }
        }
    }

    private fun fetchTechNews() {
        viewModelScope.launch {
            if (networkHelper.isConnected()) {
                _techNewsStateFlow.value = TechNewsUiState.Loading
                newsRepository.getBreakingNewsForCategory(category = CATEGORY_TECHNOLOGY)
                    .flowOn(Dispatchers.IO)
                    .catch {
                        _techNewsStateFlow.value =
                            TechNewsUiState.Failure(
                                error = it.localizedMessage ?: "Exception occurred"
                            )
                    }
                    .collectLatest {
                        it.body()?.let { newsResponse ->
                            _techNewsStateFlow.value =
                                TechNewsUiState.Success(data = newsResponse)
                        }
                    }
            } else {
                _techNewsStateFlow.value =
                    TechNewsUiState.Failure(
                        error = "No Internet"
                    )
            }
        }
    }

    private fun fetchPoliticsNews() {
        viewModelScope.launch {
            if (networkHelper.isConnected()) {
                _entertainmentNewsStateFlow.value = EntertainmentNewsUiState.Loading
                newsRepository.getBreakingNewsForCategory(category = CATEGORY_ENTERTAINMENT)
                    .flowOn(Dispatchers.IO)
                    .catch {
                        _entertainmentNewsStateFlow.value =
                            EntertainmentNewsUiState.Failure(
                                error = it.localizedMessage ?: "Exception occurred"
                            )
                    }
                    .collectLatest {
                        it.body()?.let { newsResponse ->
                            _entertainmentNewsStateFlow.value =
                                EntertainmentNewsUiState.Success(data = newsResponse)
                        }
                    }
            } else {
                _entertainmentNewsStateFlow.value =
                    EntertainmentNewsUiState.Failure(
                        error = "No Internet"
                    )
            }
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
}

sealed class HealthNewsUiState {
    data class Success(val data: NewsResponse) : HealthNewsUiState()
    data class Failure(val error: String) : HealthNewsUiState()
    object Loading : HealthNewsUiState()
}

sealed class SportsNewsUiState {
    data class Success(val data: NewsResponse) : SportsNewsUiState()
    data class Failure(val error: String) : SportsNewsUiState()
    object Loading : SportsNewsUiState()
}

sealed class TechNewsUiState {
    data class Success(val data: NewsResponse) : TechNewsUiState()
    data class Failure(val error: String) : TechNewsUiState()
    object Loading : TechNewsUiState()
}

sealed class EntertainmentNewsUiState {
    data class Success(val data: NewsResponse) : EntertainmentNewsUiState()
    data class Failure(val error: String) : EntertainmentNewsUiState()
    object Loading : EntertainmentNewsUiState()
}