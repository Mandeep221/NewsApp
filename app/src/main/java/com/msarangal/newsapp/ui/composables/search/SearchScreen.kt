package com.msarangal.newsapp.ui.composables.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.msarangal.newsapp.ui.EntertainmentNewsUiState
import com.msarangal.newsapp.ui.HealthNewsUiState
import com.msarangal.newsapp.ui.NewsViewModel
import com.msarangal.newsapp.ui.SportsNewsUiState
import com.msarangal.newsapp.ui.TechNewsUiState
import com.msarangal.newsapp.util.NewsTabsManager

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    onSearchTriggered: (String) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    healthNewsUiState: HealthNewsUiState,
    sportsNewsUiState: SportsNewsUiState,
    techNewsUiState: TechNewsUiState,
    entertainmentNewsUiState: EntertainmentNewsUiState
) {
    val newsTabs = NewsTabsManager.newsTabs
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState {
        newsTabs.size
    }
    LaunchedEffect(key1 = selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(key1 = pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopView(
            onSearchQueryChanged = onSearchQueryChanged,
            onSearchTriggered = onSearchTriggered,
        )
        Spacer(modifier = Modifier.size(16.dp))
        ScrollableTabRow(
            modifier = Modifier
                .fillMaxWidth(),
            backgroundColor = Color.Transparent,
            selectedTabIndex = selectedTabIndex,
            edgePadding = 16.dp
        ) {
            newsTabs.forEachIndexed { index, newsTab ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = {
                        selectedTabIndex = index
                    },
                    text = {
                        Text(
                            text = newsTab.title,
                            style = MaterialTheme.typography.h6,
                            fontWeight = Bold
                        )
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
        ) { pageIndex ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (pageIndex) {
                    0 -> HealthView(healthNewsUiState)
                    1 -> SportsView(sportsNewsUiState)
                    2 -> TechnologyView(techNewsUiState)
                    3 -> PoliticsView(entertainmentNewsUiState)
                }
            }
        }
    }
}

@Composable
fun TopView(
    onSearchQueryChanged: (String) -> Unit,
    onSearchTriggered: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = "Discover", style = MaterialTheme.typography.h4, fontWeight = Bold)
        Text(text = "News from all over the world", style = MaterialTheme.typography.caption)
        Spacer(modifier = Modifier.size(20.dp))
        SearchView(
            onSearchQueryChanged = onSearchQueryChanged,
            onSearchTriggered = onSearchTriggered
        )
    }
}

@Composable
fun SearchView(
    onSearchQueryChanged: (String) -> Unit,
    onSearchTriggered: (String) -> Unit
) {
    var searchQuery by remember {
        mutableStateOf("")
    }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val onSearchExplicitlyTriggered = {
        keyboardController?.hide()
        onSearchTriggered(searchQuery)
    }

    TextField(
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = MaterialTheme.colors.onSurface
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon",
                    modifier = Modifier.clickable {
                        onSearchQueryChanged("")
                    }
                )
            }
        },
        placeholder = {
            Text(text = "Search")
        },
        value = searchQuery,
        onValueChange = {
            searchQuery = it
            onSearchQueryChanged(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onKeyEvent {
                if (it.key == Key.Enter) {
                    onSearchExplicitlyTriggered()
                    true
                } else {
                    false
                }
            }
            .testTag("searchTextField"),
        shape = RoundedCornerShape(32.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchExplicitlyTriggered()
            }
        ),
        maxLines = 1,
        singleLine = true
    )
}