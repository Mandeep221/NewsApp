package com.msarangal.newsapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.cheezycode.notesample.api.NoteAPI
import com.cheezycode.notesample.api.UserAPI
import com.cheezycode.notesample.models.UserRequest
import com.msarangal.newsapp.BuildConfig
import com.msarangal.newsapp.data.local.NewsDatabase
import com.msarangal.newsapp.data.local.entities.ArticleEntity
import com.msarangal.newsapp.data.remote.NewsApi
import com.msarangal.newsapp.data.remote.model.toEntity
import com.msarangal.newsapp.domain.PracticeRepository
import com.msarangal.newsapp.util.Constants.CATEGORY_HEALTH
import com.msarangal.newsapp.util.Constants.CATEGORY_SPORTS
import javax.inject.Inject

class PracticeRepositoryImpl @Inject constructor(
    private val noteAPI: NoteAPI,
    private val userAPI: UserAPI,
    private val newsDatabase: NewsDatabase,
    private val newsApi: NewsApi
) : PracticeRepository {
    override suspend fun registerUser(userRequest: UserRequest) {
        val response = userAPI.signup(userRequest)
        Log.d("Drake", response.body().toString())
    }

    override suspend fun loginUser(userRequest: UserRequest) {
        val response = userAPI.signin(userRequest)
        Log.d("Drake", response.body().toString())
    }


    override fun fetchNewsFromDb(): LiveData<List<ArticleEntity>> {
        return newsDatabase.getNewsDao().getNews(CATEGORY_HEALTH)
    }

    override suspend fun getNews() {
        val queryMap = mapOf("category" to CATEGORY_HEALTH, "apiKey" to BuildConfig.API_KEY)
        val result = newsApi.getBreakingNewsForCategory(options = queryMap)
        if (result.isSuccessful && result.body() != null) {
            result.body()?.let { newsResponse ->
                val articleEntities = newsResponse.articles
                    .map { it.toEntity(CATEGORY_HEALTH) }
                newsDatabase.getNewsDao().insert(articleEntities)
            }
        }
    }
}