package com.msarangal.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.msarangal.newsapp.data.local.dao.NewsDao
import com.msarangal.newsapp.data.local.entities.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
}