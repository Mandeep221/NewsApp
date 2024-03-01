package com.msarangal.newsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.msarangal.newsapp.ui.theme.NewsAppTheme
import com.msarangal.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RxDemoActivity : ComponentActivity() {

    private val rxViewModel: RxDemoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                RxView("Mandeep", rxViewModel)
            }
        }
    }
}

@Composable
fun RxView(value: String, viewModel: RxDemoViewModel) {

    val task by viewModel.taskStateFlow.collectAsStateWithLifecycle()
    val result by viewModel.postState.collectAsStateWithLifecycle()
    val resul2 by viewModel.postState.collectAsStateWithLifecycle()
    val postsCount by viewModel.postCommentCount.collectAsStateWithLifecycle()
    val postLiveData by viewModel.postLiveData.observeAsState()
    val fruit by viewModel.stateFlowFruits.collectAsStateWithLifecycle("Initial")

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = fruit)

//        when (result) {
//            is Resource.Success -> {
//                Log.d("DrakeYou", "Result 1")
//                Text(text = result.data?.size.toString())
//            }
//
//            is Resource.Error -> {
//                Text(text = result.message.toString())
//            }
//
//            is Resource.Loading -> {
//                Text(text = "Loading..")
//            }
//        }
    }
}

data class Task(
    val name: String,
    val priority: Int,
    val team: List<String> = emptyList()
) {
    companion object {
        fun default() = Task("None", 0)
    }
}