package com.msarangal.newsapp.data.remote.model

data class NewsResponse(
    val articles: List<NetworkArticle>,
    val status: String,
    val totalResults: Int
)