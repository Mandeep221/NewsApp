package com.msarangal.newsapp.data.remote.model

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)