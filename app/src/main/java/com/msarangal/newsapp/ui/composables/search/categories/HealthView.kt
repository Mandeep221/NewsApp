package com.msarangal.newsapp.ui.composables.search.categories

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.msarangal.newsapp.ui.HealthNewsUiState
import com.msarangal.newsapp.ui.NewsViewModel
import com.msarangal.newsapp.ui.composables.BreakingNewsItems
import com.msarangal.newsapp.ui.composables.ErrorState
import com.msarangal.newsapp.ui.composables.NewsContent

@Composable
fun HealthView(viewModel: NewsViewModel) {
    val state by viewModel.healthNewsStateFlow.collectAsState()

    when (state) {
        is HealthNewsUiState.Failure -> {
            ErrorState(value = (state as HealthNewsUiState.Failure).error)
        }

        HealthNewsUiState.Loading -> {
            ErrorState(value = "Loading")
        }

        is HealthNewsUiState.Success -> {
            BreakingNewsItems(
                articles = (state as HealthNewsUiState.Success).data.articles,
                modifier = Modifier.fillMaxSize()
            )
           // NewsContent(response = (state as HealthNewsUiState.Success).data)
        }
    }
}