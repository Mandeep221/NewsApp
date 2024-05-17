package com.msarangal.newsapp.ui.composables.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import com.msarangal.newsapp.ui.EntertainmentNewsUiState
import com.msarangal.newsapp.ui.NewsViewModel
import com.msarangal.newsapp.ui.composables.ErrorState
import com.msarangal.newsapp.ui.composables.getColorFilter
import com.msarangal.newsapp.ui.composables.isArticleClean

@Composable
fun PoliticsView(entertainmentNewsUiState: EntertainmentNewsUiState) {
    //val state by viewModel.entertainmentNewsStateFlow.collectAsState()

    when (entertainmentNewsUiState) {
        is EntertainmentNewsUiState.Failure -> {
            ErrorState(value = entertainmentNewsUiState.error)
        }

        EntertainmentNewsUiState.Loading -> {
            ErrorState(value = "Loading")
        }

        is EntertainmentNewsUiState.Success -> {
            CategoryNewsItems(
                networkArticles = entertainmentNewsUiState.data.articles.filter {
                    isArticleClean(
                        it
                    )
                },
                modifier = Modifier.fillMaxSize(),
                colorFilter = ColorFilter.colorMatrix(getColorFilter())
            )
        }

        EntertainmentNewsUiState.UnInitialized -> TODO()
    }
}