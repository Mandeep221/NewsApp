package com.msarangal.newsapp.domain

import androidx.lifecycle.LiveData
import com.cheezycode.notesample.models.UserRequest
import com.msarangal.newsapp.data.local.entities.ArticleEntity

interface PracticeRepository {

    suspend fun registerUser(userRequest: UserRequest)
    suspend fun loginUser(userRequest: UserRequest)

    fun fetchNewsFromDb(): LiveData<List<ArticleEntity>>

    suspend fun getNews()
}