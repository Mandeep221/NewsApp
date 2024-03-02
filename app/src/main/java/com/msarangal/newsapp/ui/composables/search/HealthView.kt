package com.msarangal.newsapp.ui.composables.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import com.msarangal.newsapp.ui.HealthNewsUiState
import com.msarangal.newsapp.ui.NewsViewModel
import com.msarangal.newsapp.ui.composables.ErrorState
import com.msarangal.newsapp.ui.composables.getColorFilter
import com.msarangal.newsapp.ui.composables.isArticleClean

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
            CategoryNewsItems(
                networkArticles = (state as HealthNewsUiState.Success).data.articles.filter {
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