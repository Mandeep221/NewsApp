package com.msarangal.newsapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.msarangal.newsapp.data.remote.NewsApiService
import com.msarangal.newsapp.data.remote.model.NewsResponse
import com.msarangal.newsapp.data.remote.model.temp.Comments
import com.msarangal.newsapp.data.remote.model.temp.CommentsItem
import com.msarangal.newsapp.data.remote.model.temp.NewComments
import com.msarangal.newsapp.data.remote.model.temp.Posts
import com.msarangal.newsapp.data.remote.model.temp.PostsItem
import com.msarangal.newsapp.util.Constants.API_KEY
import com.msarangal.newsapp.util.Resource
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    val newsApiService: NewsApiService
) : NewsRepository {

    override fun getBreakingNews(): Flow<NewsResponse> = flow {
        emit(newsApiService.getBreakingNews(API_KEY))
    }

    override fun getBreakingNewsForCategory(category: String): Flow<NewsResponse> = flow {
        val queryMap = mapOf("category" to category, "apiKey" to API_KEY)
        Log.d("LoveHandle", Thread.currentThread().name)
        emit(newsApiService.getBreakingNewsForCategory(queryMap))
    }

    override fun getBreakingNewsObservable(): Observable<NewsResponse> {
        return newsApiService.getBreakingNewsObservable(API_KEY)
    }

    override fun getSinglePost(): Observable<PostsItem> {
        return newsApiService.getSinglePost("https://jsonplaceholder.typicode.com/posts/1")
    }

    override fun getSinglePostComments(postId: Int): Observable<NewComments> {
        return newsApiService.getSinglePostComments("https://jsonplaceholder.typicode.com/posts/1/comments")
    }

    override suspend fun getAllPosts(): Resource<Posts> {
        return try {
            val result = newsApiService.getAllPosts("https://jsonplaceholder.typicode.com/posts")
            Resource.Success(data = result)
        } catch (e: HttpException) {
            Resource.Error(e.message())
        } catch (e: IOException) {
            Resource.Error(e.localizedMessage ?: "IO Exception occurred")
        }


    }

    override fun getPosComments(): Observable<Comments> {
        TODO("Not yet implemented")
    }
}