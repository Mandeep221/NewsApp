package com.msarangal.newsapp.ui.composables

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.msarangal.newsapp.R
import com.msarangal.newsapp.data.remote.model.Article
import com.msarangal.newsapp.data.remote.model.NewsResponse
import com.msarangal.newsapp.ui.BreakingNewsUiState
import com.msarangal.newsapp.ui.NewsViewModel
import java.time.ZonedDateTime

@Composable
fun HomeScreen(viewModel: NewsViewModel, modifier: Modifier = Modifier) {
    val state by viewModel.breakingNewsStateFlow.collectAsState()

    when (state) {
        is BreakingNewsUiState.Failure -> {
            ErrorState(value = (state as BreakingNewsUiState.Failure).error)
        }

        BreakingNewsUiState.Loading -> {
            ErrorState(value = "Loading")
        }

        is BreakingNewsUiState.Success -> {
            NewsContent(response = (state as BreakingNewsUiState.Success).data)
        }
    }
}

@Composable
fun NewsContent(response: NewsResponse) {
    val newsOfTheDayArticle = response.articles.first {
        isArticleClean(it)
    }
    val context = LocalContext.current
    val colorFilter = ColorFilter.colorMatrix(getColorFilter())
    val imageModel = getImageModel(newsOfTheDayArticle.urlToImage, context)
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        NewsOfTheDay(
            title = newsOfTheDayArticle.title ?: "",
            imageModel = imageModel,
            modifier = Modifier.weight(0.45f),
            colorFilter = colorFilter
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 20.dp),
            text = "Breaking News",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )
        BreakingNewsItems(
            response.articles.filterIndexed { index, _ -> index > 0 }.filter { isArticleClean(it) },
            modifier = Modifier
                .weight(0.55f)
                .padding(vertical = 8.dp),
            colorFilter = colorFilter
        )
    }
}

@Composable
fun NewsOfTheDay(
    title: String,
    imageModel: Any,
    modifier: Modifier,
    colorFilter: ColorFilter
) {
    Box(
        modifier = modifier.background(color = Color.LightGray),
        contentAlignment = Alignment.BottomStart
    ) {
        AsyncImage(
            modifier = modifier
                .fillMaxSize(),
            model = imageModel,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            colorFilter = colorFilter
        )
        Column(
            modifier = modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                    )
                )
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .background(
                        color = Color.White.copy(alpha = 0.3F),
                        shape = RoundedCornerShape(size = 24.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                text = "News of the day",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.size(4.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Learn More",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp),
                    color = Color.White
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowRightAlt,
                    contentDescription = "Right Arrow",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun BreakingNewsItems(articles: List<Article>, modifier: Modifier, colorFilter: ColorFilter) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            articles.forEach {
                item {
                    val zonedDateTime = ZonedDateTime.parse(it.publishedAt)
                    val dateString =
                        "${zonedDateTime.dayOfMonth}/${zonedDateTime.monthValue}/${zonedDateTime.year}"
                    BreakingNewsItem(
                        imgUrl = it.urlToImage,
                        title = it.title ?: "",
                        time = dateString,
                        author = it.author ?: "Peter Parker",
                        colorFilter = colorFilter
                    )
                }
            }
        }
    }
}

@Composable
fun BreakingNewsItem(
    imgUrl: String?,
    title: String,
    time: String,
    author: String,
    colorFilter: ColorFilter
) {
    Column(
        modifier = Modifier.width(200.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        AsyncImage(
            model = getImageModel(imgUrl, LocalContext.current),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            colorFilter = colorFilter,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Text(
            text = title,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(14f, TextUnitType.Sp)
            ),
            lineHeight = 20.sp
        )
        Spacer(modifier = Modifier.size(2.dp))
        Text(
            text = time,
            style = MaterialTheme.typography.caption,
            color = Color.Black.copy(alpha = 0.55f)
        )

        Text(
            text = author,
            style = MaterialTheme.typography.caption,
            color = Color.Black.copy(alpha = 0.5f)
        )
    }
}

@Composable
fun ErrorState(value: String) {
    Text(text = value)
}

@Preview(showBackground = true)
@Composable
fun NewsHomeScreenPreview() {
//    NewsAppTheme {
//        NewsHomeScreen("Android")
//    }
}

fun getColorFilter(): ColorMatrix {
    val contrast = 2f // 0f..10f (1 should be default)
    val brightness = -120f // -255f..255f (0 should be default)
    val colorMatrix = floatArrayOf(
        contrast, 0f, 0f, 0f, brightness,
        0f, contrast, 0f, 0f, brightness,
        0f, 0f, contrast, 0f, brightness,
        0f, 0f, 0f, 1f, 0f
    )
    return ColorMatrix(colorMatrix)
}

private fun getImageModel(imgUrl: String?, context: Context): Any {
    return imgUrl ?: ImageRequest.Builder(context)
        .placeholder(R.drawable.ic_launcher_foreground).build()
}

fun isArticleClean(article: Article) =
    article.title.isNullOrEmpty().not() && article.title?.contains("Removed", ignoreCase = true)
        ?.not() ?: false