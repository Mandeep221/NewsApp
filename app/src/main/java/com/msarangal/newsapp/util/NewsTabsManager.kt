package com.msarangal.newsapp.util

object NewsTabsManager {
    val newsTabs = listOf(
        NewsTab(title = "Health"),
        NewsTab(title = "Sports"),
        NewsTab(title = "Technology"),
        NewsTab(title = "Entertainment")
    )
}

data class NewsTab(
    val title: String
)