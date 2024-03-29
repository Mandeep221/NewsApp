package com.msarangal.newsapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavView(
    tabs: List<NewsTabs>,
    navController: NavController,
    onClickNavItem: (String, String) -> Unit
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: NewsTabs.HOME.route
    val routes = remember {
        tabs.map { it.route }
    }

    if (currentRoute in routes) {
        BottomNavigation(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
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
                BottomNavigationItem(
                    selected = currentRoute == tab.route,
                    onClick = {
                        onClickNavItem(tab.route, currentRoute)
                    },
                    label = { Text(stringResource(id = tab.title), color = MaterialTheme.colorScheme.onSecondaryContainer) },
                    icon = { Icon(imageVector = tab.icon, contentDescription = null) },
                    alwaysShowLabel = false,
                    selectedContentColor = MaterialTheme.colorScheme.secondary,
                    unselectedContentColor = LocalContentColor.current
                )
            }
        }
    }
}