package com.msarangal.newsapp.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavView(tabs: List<NewsTabs>, navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: NewsTabs.HOME.route
    val routes = remember {
        tabs.map { it.route }
    }

    if (currentRoute in routes) {
        BottomNavigation(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    val borderSize = 1.dp.toPx()
                    drawLine(
                        color = Color.LightGray,
                        start = Offset(x = 0f, y = 0f),
                        end = Offset(x = size.width, y = 0f),
                        strokeWidth = borderSize
                    )
                },
            backgroundColor = Color.White,
            contentColor = Color.Black
        ) {
            tabs.forEach { tab ->
                val tabTitle = stringResource(id = tab.title)
                BottomNavigationItem(
                    selected = currentRoute == tab.route,
                    onClick = {
                        if (tab.route != currentRoute) {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    label = { Text(tabTitle) },
                    icon = { Icon(imageVector = tab.icon, contentDescription = null) },
                    alwaysShowLabel = false,
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = LocalContentColor.current,
                    modifier = Modifier.semantics { contentDescription = "Option $tabTitle" }
                )
            }
        }
    }
}