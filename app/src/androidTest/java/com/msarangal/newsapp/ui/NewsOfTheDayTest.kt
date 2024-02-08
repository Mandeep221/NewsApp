package com.msarangal.newsapp.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import coil.request.ImageRequest
import com.msarangal.newsapp.R
import com.msarangal.newsapp.ui.composables.NewsOfTheDay
import com.msarangal.newsapp.ui.composables.getColorFilter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsOfTheDayTest {

    /**
     *
     * We need a compose test rule to test Composables
     * Two test rules depending upon how much wen need to interact with Android components/framework
     *
     *  - createComposeRule()
     *  - createAndroidComposeRule<NewsActivity>()
     *
     * Rule of thumb is to use createComposeRule()
     */

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            NewsOfTheDay(
                title = "This is the news of the day",
                imageModel = ImageRequest.Builder(LocalContext.current)
                    .placeholder(R.drawable.ic_launcher_foreground).build(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                colorFilter = ColorFilter.colorMatrix(getColorFilter())
            )
        }
    }

    @Test
    fun news_of_the_day_tag_has_correct_text() {
        composeTestRule.onNodeWithText("This is the news of the day").assertExists()
    }
}