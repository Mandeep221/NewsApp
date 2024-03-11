package com.msarangal.newsapp.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.ui.graphics.vector.ImageVector
import com.msarangal.newsapp.R

/**
 * Default values for
 * [title] and [icon] because not all destinations will
 * need a title and an icon to show in the bottom navigation.
 */
interface NewsDestinations {
    val route: String
    @get:StringRes
    val title: Int
        get() = R.string.app_name
    val icon: ImageVector
        get() = Icons.Default.Abc
}

object Onboarding : NewsDestinations {
    override val route: String = "onboarding"
}

object Settings : NewsDestinations {
    override val route: String = "settings"
}

object News : NewsDestinations {
    override val route: String = "news"
}

object NewsHome : NewsDestinations {
    override val title: Int
        get() = R.string.tab_breaking_news
    override val icon: ImageVector = Icons.Default.Whatshot

    override val route: String = "news/home"
}

object NewsSearch : NewsDestinations {
    override val title: Int
        get() = R.string.tab_search
    override val icon: ImageVector = Icons.Default.Search

    override val route: String = "news/search"
}

object NewsProfile : NewsDestinations {
    override val title: Int
        get() = R.string.tab_profile
    override val icon: ImageVector = Icons.Default.AccountCircle

    override val route: String = "news/profile"
}