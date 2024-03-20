package com.msarangal.newsapp.data.remote

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

interface NetworkHelper {
    fun isConnected(): Boolean
}

class NetworkHelperImpl(
    private val connectivityManager: ConnectivityManager
) : NetworkHelper {

    override fun isConnected(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork
            connectivityManager.getNetworkCapabilities(activeNetwork)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        } else {
            @Suppress("DEPRECATION")
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            @Suppress("DEPRECATION")
            activeNetworkInfo?.isConnected == true
        }
    }
}