package com.msarangal.newsapp.ui.composables.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.msarangal.newsapp.ui.NewsViewModel
import com.msarangal.newsapp.ui.SportsNewsUiState
import com.msarangal.newsapp.ui.composables.ErrorState
import com.msarangal.newsapp.ui.composables.getColorFilter
import com.msarangal.newsapp.ui.composables.isArticleClean

@Composable
fun SportsView(viewModel: NewsViewModel) {
    val state by viewModel.sportsNewsStateFlow.collectAsStateWithLifecycle()

    when (state) {
        is SportsNewsUiState.Failure -> {
            ErrorState(value = (state as SportsNewsUiState.Failure).error)
        }

        SportsNewsUiState.Loading -> {
            ErrorState(value = "Loading")
        }

        is SportsNewsUiState.Success -> {
            CategoryNewsItems(
                articles = (state as SportsNewsUiState.Success).data.articles.filter {
                    isArticleClean(
                        it
                    )
                },
                modifier = Modifier.fillMaxSize(),
                colorFilter = ColorFilter.colorMatrix(getColorFilter())
            )
        }
    }
}