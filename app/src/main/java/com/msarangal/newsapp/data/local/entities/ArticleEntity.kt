package com.msarangal.newsapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.msarangal.newsapp.data.model.Article

@Entity(tableName = "article_table")
data class ArticleEntity(
    val imgUrl: String,
    val title: String,
    val date: String,
    val author: String,
    val category: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

fun ArticleEntity.asExternalModel() = Article(
    title = title,
    author = author,
    imgUrl = imgUrl,
    dateTime = date,
    category = category
)