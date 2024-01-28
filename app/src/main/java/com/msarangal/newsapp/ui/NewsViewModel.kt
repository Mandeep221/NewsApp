package com.msarangal.newsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msarangal.newsapp.data.NewsRepository
import com.msarangal.newsapp.data.remote.model.NewsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            _stateFlow.value = NewsUiState.Loading
            newsRepository.getBreakingNewsForCategory(category = "sports")
                .catch {
                    _stateFlow.value =
                        NewsUiState.Failure(error = it.localizedMessage ?: "Exception occurred")
                }
                .collectLatest {
                    _stateFlow.value = NewsUiState.Success(data = it)
                }
        }
    }
}

sealed class NewsUiState {
    data class Success(val data: NewsResponse) : NewsUiState()
    data class Failure(val error: String) : NewsUiState()
    object Loading : NewsUiState()
}