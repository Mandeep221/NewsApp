package com.msarangal.newsapp

import android.app.Application
import com.msarangal.newsapp.data.remote.NetworkManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class NewsApplication : Application() {

    @Inject
    lateinit var networkManager: NetworkManager

    override fun onCreate() {
        super.onCreate()
        networkManager.registerNetworkCallback()
    }

    override fun onTerminate() {
        super.onTerminate()
        networkManager.unregisterNetworkCallback()
    }
}