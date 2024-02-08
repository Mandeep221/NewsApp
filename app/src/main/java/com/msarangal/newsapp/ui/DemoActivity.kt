package com.msarangal.newsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.msarangal.newsapp.ui.theme.NewsAppTheme

class DemoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                DemoComposable()
            }
        }
    }
}

@Composable
fun DemoComposable() {
    Text(text = "Mandeep is my name")
}
