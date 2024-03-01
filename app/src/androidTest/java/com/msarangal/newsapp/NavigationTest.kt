package com.msarangal.newsapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.msarangal.newsapp.ui.NewsActivity
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<NewsActivity>()

    @Test
    fun newsApp_navigateToSearchScreen() {
        composeTestRule
            .onNodeWithContentDescription("Option Search")
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("Search Screen View")
            .assertIsDisplayed()
    }
}