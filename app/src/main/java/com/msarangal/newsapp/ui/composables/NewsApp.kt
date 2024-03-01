package com.msarangal.newsapp.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.msarangal.newsapp.ui.NewsViewModel
import com.msarangal.newsapp.ui.composables.search.SearchScreen
import com.msarangal.newsapp.ui.theme.NewsAppTheme

object GlobalDestinations {
    const val ONBOARDING_ROUTE = "onboarding"
    const val SETTINGS_ROUTE = "settings"
    const val NEWS_ROUTE = "news"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsApp(
    viewModel: NewsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val navController = rememberNavController()

    NewsAppTheme {
        val tabs = NewsTabs.values().toList()
        Scaffold(
            topBar = { TopBarView() },
            bottomBar = { BottomNavView(tabs = tabs, navController = navController) }
        ) {
            val modifier = Modifier
                .fillMaxSize()
                .padding(it)

            NavHost(
                modifier = modifier,
                navController = navController,
                startDestination = GlobalDestinations.NEWS_ROUTE
            ) {
                composable(
                    route = GlobalDestinations.ONBOARDING_ROUTE
                ) {
                    OnBoardingScreen()
                }

                composable(
                    route = GlobalDestinations.SETTINGS_ROUTE
                ) {
                    SettingsScreen()
                }

                navigation(
                    route = GlobalDestinations.NEWS_ROUTE,
                    startDestination = NewsDestinations.HOME_ROUTE
                ) {
                    news(
                        newsViewModel = viewModel,
                        navController = navController,
                        modifier = modifier
                    )
                }
            }
        }
    }
}

fun NavGraphBuilder.news(
    newsViewModel: NewsViewModel,
    navController: NavController, // this needs to be removed. pass action lambda instead
    modifier: Modifier
) {
    composable(
        route = NewsTabs.HOME.route
    ) {
        HomeScreen(
            viewModel = newsViewModel,
            modifier = modifier
        )
    }

    composable(route = NewsTabs.SEARCH.route) {
        SearchScreen(newsViewModel)
    }

    composable(route = NewsTabs.PROFILE.route) {
        ProfileScreen()
    }
}