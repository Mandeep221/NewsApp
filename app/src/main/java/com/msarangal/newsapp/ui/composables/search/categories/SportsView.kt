package com.msarangal.newsapp.ui.composables.search.categories

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.msarangal.newsapp.ui.NewsViewModel
import com.msarangal.newsapp.ui.SportsNewsUiState
import com.msarangal.newsapp.ui.composables.BreakingNewsItems
import com.msarangal.newsapp.ui.composables.ErrorState

@Composable
fun SportsView(viewModel: NewsViewModel) {
    val state by viewModel.sportsNewsStateFlow.collectAsState()

    when (state) {
        is SportsNewsUiState.Failure -> {
            ErrorState(value = (state as SportsNewsUiState.Failure).error)
        }

        SportsNewsUiState.Loading -> {
            ErrorState(value = "Loading")
        }

        is SportsNewsUiState.Success -> {
            BreakingNewsItems(
                articles = (state as SportsNewsUiState.Success).data.articles,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}