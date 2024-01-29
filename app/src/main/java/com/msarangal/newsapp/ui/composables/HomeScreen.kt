package com.msarangal.newsapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.msarangal.newsapp.data.remote.model.Article
import com.msarangal.newsapp.data.remote.model.NewsResponse
import com.msarangal.newsapp.ui.NewsUiState
import com.msarangal.newsapp.ui.NewsViewModel

@Composable
fun NewsContent(response: NewsResponse) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NewsOfTheDay(response.articles[0], modifier = Modifier.weight(1f))
        BreakingNewsItems(
            response.articles.filterIndexed { index, _ -> index > 0 },
            modifier = Modifier.weight(3f)
        )
    }
}

@Composable
fun NewsOfTheDay(article: Article, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = Color.Blue)
    ) {
        Text(text = article.title)
    }
}

@Composable
fun HomeScreen(viewModel: NewsViewModel, modifier: Modifier = Modifier) {
    val state by viewModel.stateFlow.collectAsState()

    when (state) {
        is NewsUiState.Failure -> {
            ErrorState(value = (state as NewsUiState.Failure).error)
        }

        NewsUiState.Loading -> {
            ErrorState(value = "Loading")
        }

        is NewsUiState.Success -> {
            NewsContent(response = (state as NewsUiState.Success).data)
        }
    }
}

@Composable
fun BreakingNewsItems(articles: List<Article>, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = Color.Yellow)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            articles.forEach {
                item {
                    Text(text = it.title)
                }
            }
        }
    }
}

@Composable
fun ErrorState(value: String) {
    Text(text = value)
}

@Preview(showBackground = true)
@Composable
fun NewsHomeScreenPreview() {
//    NewsAppTheme {
//        NewsHomeScreen("Android")
//    }
}