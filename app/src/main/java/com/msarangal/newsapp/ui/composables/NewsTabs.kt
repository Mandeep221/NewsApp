package com.msarangal.newsapp.ui.composables

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.ui.graphics.vector.ImageVector
import com.msarangal.newsapp.R
import com.msarangal.newsapp.ui.composables.NewsDestinations.HOME_ROUTE
import com.msarangal.newsapp.ui.composables.NewsDestinations.PROFILE_ROUTE
import com.msarangal.newsapp.ui.composables.NewsDestinations.SEARCH_ROUTE

enum class NewsTabs(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    HOME(R.string.tab_breaking_news, Icons.Default.Whatshot, HOME_ROUTE),
    SEARCH(R.string.tab_search, Icons.Default.Search, SEARCH_ROUTE),
    PROFILE(R.string.tab_profile, Icons.Default.AccountCircle, PROFILE_ROUTE)
}

object NewsDestinations {
    const val HOME_ROUTE = "news/home"
    const val SEARCH_ROUTE = "news/search"
    const val PROFILE_ROUTE = "news/profile"
}