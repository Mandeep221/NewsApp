package com.msarangal.newsapp.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.msarangal.newsapp.data.NewsRepository
import com.msarangal.newsapp.data.NewsRepositoryImpl
import com.msarangal.newsapp.data.local.NewsDatabase
import com.msarangal.newsapp.data.remote.NetworkHelper
import com.msarangal.newsapp.data.remote.NetworkHelperImpl
import com.msarangal.newsapp.data.remote.NetworkManager
import com.msarangal.newsapp.data.remote.NewsApi
import com.msarangal.newsapp.util.Constants.BASE_URL
import com.msarangal.newsapp.util.Constants.NEWS_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    fun providesRoomDb(@ApplicationContext context: Context): NewsDatabase = Room.databaseBuilder(
        context = context, klass = NewsDatabase::class.java, name = NEWS_DB
    ).build()

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
    fun providesRetrofitInstance(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun providesNewsApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)

    @Singleton
    @Provides
    fun providesNewsRepository(newsApi: NewsApi): NewsRepository =
        NewsRepositoryImpl(newsApi = newsApi)

    @Singleton
    @Provides
    fun providesConnectivityManager(@ApplicationContext context: Context) =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Singleton
    @Provides
    fun providesNetworkManager(connectivityManager: ConnectivityManager): NetworkManager =
        NetworkManager(connectivityManager = connectivityManager)

    @Singleton
    @Provides
    fun providesNetworkHelper(connectivityManager: ConnectivityManager): NetworkHelper =
        NetworkHelperImpl(connectivityManager = connectivityManager)
}