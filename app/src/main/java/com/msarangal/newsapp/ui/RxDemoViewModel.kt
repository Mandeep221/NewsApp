package com.msarangal.newsapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msarangal.newsapp.data.NewsRepository
import com.msarangal.newsapp.data.remote.model.temp.Posts
import com.msarangal.newsapp.util.Constants
import com.msarangal.newsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class RxDemoViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("TestDrake", throwable.toString())
    }

    private val flowFruits = viewModelScope.launch {
        val flowFruits = flow {
            emit("Grapes")
            delay(1000)
            emit("Cherry")
            delay(1000)
            emit("Blueberry")
            delay(1000)
        }

        val flowBooze = flow {
            emit("Wine")
            delay(1000)
            emit("Rum")
            delay(1000)
            emit("Whiskey")
            delay(1000)
        }

        flowFruits.combine(flowBooze) { fruit, booze ->
            "Fruit: $fruit, Booze: $booze"
        }.collect {
            Log.d("Sheru", it)
        }
    }

    val flowOfFruits = flow {
        emit("Grapes")
        delay(1000)
        emit("Cherry")
        delay(1000)
        emit("Mango")
        delay(1000)
    }

    private val _post = MutableStateFlow<List<String>>(emptyList())
    val posts = _post.asStateFlow()

    private val _postCommentCount = MutableStateFlow(0)
    val postCommentCount = _postCommentCount.asStateFlow()

    private val _postState = MutableStateFlow<Resource<Posts>>(Resource.Loading())
    val postState = _postState.asStateFlow()

    private val _postLiveData = MutableLiveData<Resource<Posts>>()
    val postLiveData: LiveData<Resource<Posts>> = _postLiveData

    private val disposables = CompositeDisposable()

    private val taskObservable: Observable<Task> = Observable.fromIterable(getTasks())
        .subscribeOn(Schedulers.io())
        .doOnNext {
            Log.d("Drake", Thread.currentThread().name)
        }
        .filter { task ->
            Thread.sleep(1000)
            true
        }
        .observeOn(AndroidSchedulers.mainThread())

//    private val _stateFlowFruits = MutableStateFlow("")
//    val stateFlowFruits = _stateFlowFruits.asStateFlow()
    private val _stateFlowFruits = MutableSharedFlow<String>(0)
    val stateFlowFruits = _stateFlowFruits.asSharedFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            _stateFlowFruits.emit("Manu")
            delay(3000)
            _stateFlowFruits.emit("Nanu")
        }

        disposables.add(
            taskObservable.subscribe(
                {// onNext
                    Log.d("Drake", Thread.currentThread().name)
                    updateState(task = it)
                },
                {// onError
                    updateState(
                        Task(
                            name = "Error",
                            priority = -1
                        )
                    )
                })
        )

        disposables.add(Observable.fromIterable(getTasks())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { Observable.fromIterable(it.team) }
            .subscribe(
                {
                    Log.d("Draky", it)
                },
                {

                }
            )
        )

        // posts
        disposables.add(newsRepository.getSinglePost()
            .flatMap {
                newsRepository.getSinglePostComments(it.id)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _postCommentCount.value = it.size
                },
                {
                    Log.d("Draky", it.stackTraceToString())
                    _postCommentCount.value = -100
                }
            )
        )

        viewModelScope.launch {
            when (val result = newsRepository.getAllPosts()) {
                is Resource.Success -> {
                    result.data?.let { posts ->
                        _postState.value = Resource.Success(posts)
                    }
                }

                is Resource.Error -> {
                    _postState.value = Resource.Error(result.message ?: "Exception occurred")
                }

                is Resource.Loading -> {
                    _postState.value = Resource.Loading()
                }
            }
        }


        val customScope = CoroutineScope(Dispatchers.Main + coroutineExceptionHandler)

        viewModelScope.launch(coroutineExceptionHandler) {
            supervisorScope {
                launch {// child 1
                    delay(3000)
                    throw Exception("Error: Child 1")
                }

                launch {// child 2
                    delay(3000)
                    throw Exception("Error:  child 2")
                }
            }
        }
    }

    private suspend fun childOne() {
        withContext(Dispatchers.IO) {
            delay(3000)
            Log.d("TestDrake", "Child One executed")
        }
    }

    private suspend fun childTwo() {
        withContext(Dispatchers.IO) {
            delay(3000)
            Log.d("TestDrake", "Child Two executed")
        }
    }

    private suspend fun callWithAsyncAwait() {
        coroutineScope {
            val healthNews = async {
                delay(4000)
                Log.d("DrakeAsyncMan", "Health n/w request started..")
                newsRepository.getBreakingNewsForCategory(Constants.CATEGORY_HEALTH)
            }
            val sportsNews = async {
                delay(4000)
                Log.d("DrakeAsyncMan", "Sports n/w request started..")
                newsRepository.getBreakingNewsForCategory(Constants.CATEGORY_SPORTS)
            }
            healthNews.await()
                .catch {
                    Log.d("DrakeAsync", "Health news Exception: ${it.stackTraceToString()}")
                }
                .collectLatest {
                    Log.d("DrakeAsync", "Health news: ${it.articles.size} articles retrieved")
                }
            sportsNews.await()
                .catch {
                    Log.d("DrakeAsync", "Sports news Exception: ${it.stackTraceToString()}")
                }
                .collectLatest {
                    Log.d("DrakeAsync", "Sports news: ${it.articles.size} articles retrieved")
                }
        }
    }

    private suspend fun callWithLaunch() {
        newsRepository.getBreakingNewsForCategory(Constants.CATEGORY_HEALTH)
            .catch {
                Log.d("DrakeAsync", "Health news Exception: ${it.stackTraceToString()}")
            }
            .collectLatest {
                delay(4000)
                Log.d("DrakeAsync", "Health news: ${it.articles.size} articles retrieved")
            }
        newsRepository.getBreakingNewsForCategory(Constants.CATEGORY_SPORTS)
            .catch {
                Log.d("DrakeAsync", "Sports news Exception: ${it.stackTraceToString()}")
            }
            .collectLatest {
                delay(4000)
                Log.d("DrakeThreadInfo", Thread.currentThread().name)
                Log.d("DrakeAsync", "Sports news: ${it.articles.size} articles retrieved")
            }
    }

    private val _taskStateFlow: MutableStateFlow<Task> = MutableStateFlow(Task.default())
    val taskStateFlow = _taskStateFlow.asStateFlow()

    private fun updateState(task: Task) {
        _taskStateFlow.value = task
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear() // Observables can still be subscribed to again.
        // disposables.dispose() : Observables can no longer be subscribed to
    }
}

fun getTasks(): List<Task> {
    return listOf(
        Task(
            name = "Learn RxAndroid",
            priority = 1,
            team = listOf("Mandeep", "Navdeep", "Randeep")
        ),
        Task(
            name = "Revise Learning Notes",
            priority = 2,
            team = listOf("Josh", "Walsh", "Matt")
        ),
        Task(
            name = "Additional learning",
            priority = 3,
            team = listOf("Lukas", "Rumi", "Helga")
        )
    )
}

sealed class PostsUiState {
    data class Success(val data: Resource<Posts>) : PostsUiState()
    data class Error(val msg: String) : PostsUiState()
    object Loading : PostsUiState()
}