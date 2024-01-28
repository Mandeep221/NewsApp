package com.msarangal.newsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.msarangal.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NewsHomeScreen(viewModel = newsViewModel)
                }
            }
        }
    }
}

@Composable
fun NewsHomeScreen(viewModel: NewsViewModel, modifier: Modifier = Modifier) {
    val state by viewModel.stateFlow.collectAsState()

    when (state) {
        is NewsUiState.Failure -> {
            NewsContent(value = (state as NewsUiState.Failure).error)
        }

        NewsUiState.Loading -> {
            NewsContent(value = "Loading")
        }

        is NewsUiState.Success -> {
            NewsContent(value = (state as NewsUiState.Success).data.totalResults.toString())
        }
    }
}

@Composable
fun NewsContent(value: String) {
    Text(text = value)
}

@Preview(showBackground = true)
@Composable
fun NewsHomeScreenPreview() {
//    NewsAppTheme {
//        NewsHomeScreen("Android")
//    }
}