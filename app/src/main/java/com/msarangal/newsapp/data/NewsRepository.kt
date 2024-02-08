package com.msarangal.newsapp.data

import com.msarangal.newsapp.data.remote.model.NewsResponse
import com.msarangal.newsapp.data.remote.model.temp.Comments
import com.msarangal.newsapp.data.remote.model.temp.NewComments
import com.msarangal.newsapp.data.remote.model.temp.Posts
import com.msarangal.newsapp.data.remote.model.temp.PostsItem
import com.msarangal.newsapp.util.Resource
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getBreakingNews(): Flow<NewsResponse>
    fun getBreakingNewsForCategory(category: String): Flow<NewsResponse>

    fun getBreakingNewsObservable(): Observable<NewsResponse>

    fun getSinglePost(): Observable<PostsItem>

    fun getPosComments(): Observable<Comments>

    fun getSinglePostComments(postId: Int): Observable<NewComments>

    suspend fun getAllPosts(): Resource<Posts>
}