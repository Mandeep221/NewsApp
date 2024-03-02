package com.msarangal.newsapp.ui.composables.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.msarangal.newsapp.data.remote.model.NetworkArticle
import com.msarangal.newsapp.ui.composables.getImageModel
import java.time.ZonedDateTime
import java.util.Locale

@Composable
fun CategoryNewsItems(networkArticles: List<NetworkArticle>, modifier: Modifier, colorFilter: ColorFilter) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        networkArticles.forEach {
            item {
                val zonedDateTime = ZonedDateTime.parse(it.publishedAt)
                val dateString =
                    "${zonedDateTime.month.getDisplayName(java.time.format.TextStyle.FULL, Locale.CANADA)} ${zonedDateTime.dayOfMonth}, ${zonedDateTime.year}"
                CategoryNewsItem(
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

@Composable
fun CategoryNewsItem(
    imgUrl: String?,
    title: String,
    time: String,
    author: String,
    colorFilter: ColorFilter
) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        AsyncImage(
            model = getImageModel(imgUrl, LocalContext.current),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            colorFilter = colorFilter,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .size(64.dp)
        )

        Column(
            modifier = Modifier.height(64.dp).weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(14f, TextUnitType.Sp)
                ),
                lineHeight = 20.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = time,
                style = MaterialTheme.typography.caption,
                color = Color.Black.copy(alpha = 0.55f)
            )
        }
    }
}