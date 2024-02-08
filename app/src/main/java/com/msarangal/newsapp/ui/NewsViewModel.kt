package com.msarangal.newsapp.ui

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msarangal.newsapp.data.NewsRepository
import com.msarangal.newsapp.data.remote.model.Article
import com.msarangal.newsapp.data.remote.model.NewsResponse
import com.msarangal.newsapp.util.Constants.CATEGORY_HEALTH
import com.msarangal.newsapp.util.Constants.CATEGORY_ENTERTAINMENT
import com.msarangal.newsapp.util.Constants.CATEGORY_SPORTS
import com.msarangal.newsapp.util.Constants.CATEGORY_TECHNOLOGY
import com.msarangal.newsapp.util.Constants.SEARCH_QUERY
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private lateinit var disposable: Disposable

    val categoryMap = mapOf(
        CATEGORY_HEALTH to 0,
        CATEGORY_SPORTS to 1,
        CATEGORY_TECHNOLOGY to 2,
        CATEGORY_ENTERTAINMENT to 3
    )

    val searchQuery = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")

    var initialCategoryIndex = categoryMap.values.first()

    private val _breakingNewsStateFlow =
        MutableStateFlow<BreakingNewsUiState>(BreakingNewsUiState.Loading)
    val breakingNewsStateFlow = _breakingNewsStateFlow.asStateFlow()

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
        //fetchBreakingNews()
        fetchHealthNews()
        fetchSportsNews()
        fetchTechNews()
        fetchPoliticsNews()

        // Rx
        fetchBreakingNewsObservable()
    }

    private fun fetchBreakingNews() {
        viewModelScope.launch {

            _breakingNewsStateFlow.value = BreakingNewsUiState.Loading
            newsRepository.getBreakingNews()
                .catch {
                    _breakingNewsStateFlow.value =
                        BreakingNewsUiState.Failure(
                            error = it.localizedMessage ?: "Exception occurred"
                        )
                }
                .collectLatest {
                    _breakingNewsStateFlow.value = BreakingNewsUiState.Success(data = it)
                }
        }
    }

    private fun fetchBreakingNewsObservable() {
        disposable = newsRepository.getBreakingNewsObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _breakingNewsStateFlow.value = BreakingNewsUiState.Success(data = it)
            }, {
                _breakingNewsStateFlow.value =
                    BreakingNewsUiState.Failure(
                        error = it.localizedMessage ?: "Exception occurred"
                    )
            }, {

            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    private fun fetchHealthNews() {
        viewModelScope.launch {
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
                    _healthNewsStateFlow.value = HealthNewsUiState.Success(data = it)
                }
        }
    }

    private fun fetchSportsNews() {
        viewModelScope.launch {
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
                    _sportsNewsStateFlow.value = SportsNewsUiState.Success(data = it)
                }
        }
    }

    private fun fetchTechNews() {
        viewModelScope.launch {
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
                    _techNewsStateFlow.value = TechNewsUiState.Success(data = it)
                }
        }
    }

    private fun fetchPoliticsNews() {
        viewModelScope.launch {
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
                    _entertainmentNewsStateFlow.value = EntertainmentNewsUiState.Success(data = it)
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