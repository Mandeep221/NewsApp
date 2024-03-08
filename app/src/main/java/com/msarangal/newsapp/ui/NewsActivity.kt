package com.msarangal.newsapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.msarangal.newsapp.base.AnalyticsLogger
import com.msarangal.newsapp.base.AnalyticsLoggerImpl
import com.msarangal.newsapp.base.DeepLinkHandler
import com.msarangal.newsapp.base.DeeplinkHandlerImpl
import com.msarangal.newsapp.ui.composables.NewsApp
import com.msarangal.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : ComponentActivity(),
    AnalyticsLogger by AnalyticsLoggerImpl(),
    DeepLinkHandler by DeeplinkHandlerImpl() {

    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLifecycleOwner(this)
        handleDeeplink(this, intent)

        val obj = MyFactory().getObj<AnalyticsLoggerImpl>()
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    NewsApp(viewModel = newsViewModel)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleDeeplink(this, intent)
    }
}

class MyFactory {
    inline fun <reified T : Any> getObj(): T? {
        return when (T::class) {
            AnalyticsLoggerImpl::class -> AnalyticsLoggerImpl() as? T

            DeeplinkHandlerImpl::class -> DeeplinkHandlerImpl() as? T
            else -> null
        }
    }
}