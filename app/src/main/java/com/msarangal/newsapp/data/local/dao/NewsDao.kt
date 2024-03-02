package com.msarangal.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.msarangal.newsapp.data.local.entities.ArticleEntity

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articleEntities: List<ArticleEntity>)

    @Query("Select * from article_table where category = :category")
    fun getNews(category: String): List<ArticleEntity>

    @Query("Delete from article_table")
    fun deleteAllNews()
}