package com.msarangal.newsapp.ui.composables

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.ui.graphics.vector.ImageVector
import com.msarangal.newsapp.R
import com.msarangal.newsapp.navigation.NewsHome
import com.msarangal.newsapp.navigation.NewsProfile
import com.msarangal.newsapp.navigation.NewsSearch

enum class NewsTabs(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    HOME(R.string.tab_breaking_news, Icons.Default.Whatshot, NewsHome.route),
    SEARCH(R.string.tab_search, Icons.Default.Search, NewsSearch.route),
    PROFILE(R.string.tab_profile, Icons.Default.AccountCircle, NewsProfile.route)
}