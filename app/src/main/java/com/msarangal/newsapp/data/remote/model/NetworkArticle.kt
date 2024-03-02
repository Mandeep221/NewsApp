package com.msarangal.newsapp.data.remote.model

import com.msarangal.newsapp.data.local.entities.ArticleEntity

data class NetworkArticle(
    val author: String?,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String?,
    val url: String,
    val urlToImage: String?
)

fun NetworkArticle.toEntity(category: String) = ArticleEntity(
    imgUrl = urlToImage ?: "No image Url",
    title = title ?: "No title",
    date = publishedAt,
    author = author ?: "XYZ",
    category = category
)