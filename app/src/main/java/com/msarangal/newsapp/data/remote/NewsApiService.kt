package com.msarangal.newsapp.data.remote

import androidx.lifecycle.LiveData
import com.msarangal.newsapp.data.remote.model.NewsResponse
import com.msarangal.newsapp.data.remote.model.temp.Comments
import com.msarangal.newsapp.data.remote.model.temp.CommentsItem
import com.msarangal.newsapp.data.remote.model.temp.NewComments
import com.msarangal.newsapp.data.remote.model.temp.Posts
import com.msarangal.newsapp.data.remote.model.temp.PostsItem
import com.msarangal.newsapp.util.Resource
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface NewsApiService {

    @GET("/v2/top-headlines?language=en")
    suspend fun getBreakingNews(@Query("apiKey") key: String): NewsResponse

    @GET("/v2/top-headlines?language=en")
    suspend fun getBreakingNewsForCategory(@QueryMap options: Map<String, String>): NewsResponse

    @GET("/v2/everything")
    suspend fun getNewsSearchResults(@QueryMap options: Map<String, String>): NewsResponse

    @GET("/v2/top-headlines?language=en")
    fun getBreakingNewsObservable(@Query("apiKey") key: String): Observable<NewsResponse>

    @GET
    fun getPosts(@Url url: String): Observable<Posts>

    @GET
    fun getSinglePost(@Url url: String): Observable<PostsItem>

    @GET
    fun getSinglePostComments(@Url url: String): Observable<NewComments>

    @GET
    suspend fun getAllPosts(@Url url: String): Posts
}