package com.msarangal.newsapp.ui.composables

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.msarangal.newsapp.R
import com.msarangal.newsapp.ui.composables.NewsDestinations.HOME_ROUTE
import com.msarangal.newsapp.ui.composables.NewsDestinations.PROFILE_ROUTE
import com.msarangal.newsapp.ui.composables.NewsDestinations.SEARCH_ROUTE

enum class NewsTabs(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    HOME(R.string.tab_home, R.drawable.ic_grain, HOME_ROUTE),
    SEARCH(R.string.tab_search, R.drawable.ic_search, SEARCH_ROUTE),
    PROFILE(R.string.tab_profile, R.drawable.ic_featured, PROFILE_ROUTE)
}

object NewsDestinations {
    const val HOME_ROUTE = "news/home"
    const val SEARCH_ROUTE = "news/search"
    const val PROFILE_ROUTE = "news/profile"
}