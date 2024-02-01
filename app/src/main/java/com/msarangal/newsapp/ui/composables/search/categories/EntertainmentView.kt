package com.msarangal.newsapp.ui.composables.search.categories

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.msarangal.newsapp.ui.NewsViewModel
import com.msarangal.newsapp.ui.EntertainmentNewsUiState
import com.msarangal.newsapp.ui.composables.BreakingNewsItems
import com.msarangal.newsapp.ui.composables.ErrorState

@Composable
fun PoliticsView(viewModel: NewsViewModel) {
    val state by viewModel.entertainmentNewsStateFlow.collectAsState()

    when (state) {
        is EntertainmentNewsUiState.Failure -> {
            ErrorState(value = (state as EntertainmentNewsUiState.Failure).error)
        }

        EntertainmentNewsUiState.Loading -> {
            ErrorState(value = "Loading")
        }

        is EntertainmentNewsUiState.Success -> {
            BreakingNewsItems(
                articles = (state as EntertainmentNewsUiState.Success).data.articles,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}