package com.msarangal.newsapp.ui.composables.search.categories

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.msarangal.newsapp.ui.NewsViewModel
import com.msarangal.newsapp.ui.TechNewsUiState
import com.msarangal.newsapp.ui.composables.BreakingNewsItems
import com.msarangal.newsapp.ui.composables.ErrorState

@Composable
fun TechnologyView(viewModel: NewsViewModel) {
    val state by viewModel.techNewsStateFlow.collectAsState()

    when (state) {
        is TechNewsUiState.Failure -> {
            ErrorState(value = (state as TechNewsUiState.Failure).error)
        }

        TechNewsUiState.Loading -> {
            ErrorState(value = "Loading")
        }

        is TechNewsUiState.Success -> {
            BreakingNewsItems(
                articles = (state as TechNewsUiState.Success).data.articles,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}