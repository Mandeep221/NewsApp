package com.msarangal.newsapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.msarangal.newsapp.navigation.News
import com.msarangal.newsapp.navigation.NewsHome
import com.msarangal.newsapp.navigation.NewsProfile
import com.msarangal.newsapp.navigation.NewsSearch
import com.msarangal.newsapp.navigation.Onboarding
import com.msarangal.newsapp.navigation.Settings
import com.msarangal.newsapp.ui.NewsViewModel
import com.msarangal.newsapp.ui.composables.search.SearchScreen
import com.msarangal.newsapp.ui.theme.NewsAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsApp(
    viewModel: NewsViewModel
) {
    val navController = rememberNavController()

    NewsAppTheme {
        val tabs = NewsTabs.values().toList()
        Scaffold(
            topBar = { TopBarView() },
            bottomBar = {
                BottomNavView(
                    tabs = tabs,
                    navController = navController
                ) { selectedTabRoute, currentTabRoute ->
                    handleOnClickBottomNavItem(selectedTabRoute, currentTabRoute, navController)
                }
            }
        ) {
            val modifier = Modifier
                .fillMaxSize()
                .padding(it)

            NavHost(
                modifier = modifier.background(color = MaterialTheme.colorScheme.surfaceVariant),
                navController = navController,
                startDestination = News.route
            ) {
                composable(
                    route = Onboarding.route
                ) {
                    OnBoardingScreen()
                }

                composable(
                    route = Settings.route
                ) {
                    SettingsScreen()
                }

                navigation(
                    route = News.route,
                    startDestination = NewsHome.route
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

private fun handleOnClickBottomNavItem(
    selectedTabRoute: String,
    currentTabRoute: String,
    navController: NavController
) {
    if (selectedTabRoute != currentTabRoute) {
        navController.navigate(selectedTabRoute) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = false
            restoreState = true
        }
    }
}

fun NavGraphBuilder.news(
    newsViewModel: NewsViewModel,
    navController: NavController,
    modifier: Modifier
) {
    composable(
        route = NewsHome.route
    ) {
        HomeScreen(
            viewModel = newsViewModel,
            modifier = modifier
        )
    }

    composable(
        route = NewsSearch.routeWithArgs,
        arguments = NewsSearch.arguments,
        deepLinks = NewsSearch.deepLinks
    ) {
        SearchScreen(newsViewModel)
    }

    composable(route = NewsProfile.route) {
        ProfileScreen()
    }
}