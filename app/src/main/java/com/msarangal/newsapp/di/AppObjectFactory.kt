package com.msarangal.newsapp.di

import android.content.Context
import androidx.room.Room
import com.msarangal.newsapp.data.NewsRepository
import com.msarangal.newsapp.data.NewsRepositoryImpl
import com.msarangal.newsapp.data.local.NewsDatabase
import com.msarangal.newsapp.data.remote.NewsApiService
import com.msarangal.newsapp.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppObjectFactory {
    val newsDb: NewsDatabase
    val okHttpClient: OkHttpClient
    val newsApiService: NewsApiService
    val newsApiRepository: NewsRepository
}

class AppObjectFactoryImpl(
    private val appContext: Context
) : AppObjectFactory {
    override val newsDb: NewsDatabase by lazy {
        Room.databaseBuilder(
            context = appContext, klass = NewsDatabase::class.java, name = Constants.NEWS_DB
        ).build()
    }
    override val okHttpClient: OkHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
    override val newsApiService: NewsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }
    override val newsApiRepository: NewsRepository by lazy {
        NewsRepositoryImpl(newsApiService = newsApiService)
    }
}