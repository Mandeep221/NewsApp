package com.msarangal.newsapp.ui.practice

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.msarangal.newsapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PracticeActivity : FragmentActivity() {

    private val viewModel: PracticeViewModel by viewModels<PracticeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            // Do something
        }, 4000)
    }
}