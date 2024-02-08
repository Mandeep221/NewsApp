package com.msarangal.newsapp.di

import com.github.leonardoxh.livedatacalladapter.LiveDataCallAdapterFactory
import com.msarangal.newsapp.data.NewsRepository
import com.msarangal.newsapp.data.NewsRepositoryImpl
import com.msarangal.newsapp.data.remote.NewsApiService
import com.msarangal.newsapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesOkhttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofitInstance(client: OkHttpClient): NewsApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
        .build()
        .create(NewsApiService::class.java)

    @Singleton
    @Provides
    fun providesNewsRepository(newsApiService: NewsApiService): NewsRepository =
        NewsRepositoryImpl(newsApiService = newsApiService)
}