package com.msarangal.newsapp.ui.composables.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import com.msarangal.newsapp.ui.NewsViewModel
import com.msarangal.newsapp.ui.TechNewsUiState
import com.msarangal.newsapp.ui.composables.ErrorState
import com.msarangal.newsapp.ui.composables.getColorFilter
import com.msarangal.newsapp.ui.composables.isArticleClean

@Composable
fun TechnologyView(techNewsUiState: TechNewsUiState) {
   // val state by viewModel.techNewsStateFlow.collectAsState()

    when (techNewsUiState) {
        is TechNewsUiState.Failure -> {
            ErrorState(value = techNewsUiState.error)
        }

        TechNewsUiState.Loading -> {
            ErrorState(value = "Loading")
        }

        is TechNewsUiState.Success -> {
            CategoryNewsItems(
                networkArticles = techNewsUiState.data.articles.filter {
                    isArticleClean(
                        it
                    )
                },
                modifier = Modifier.fillMaxSize(),
                colorFilter = ColorFilter.colorMatrix(getColorFilter())
            )
        }

        TechNewsUiState.UnInitialized -> TODO()
    }
}