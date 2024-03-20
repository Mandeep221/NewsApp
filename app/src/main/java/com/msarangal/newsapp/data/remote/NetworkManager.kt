package com.msarangal.newsapp.data.remote

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NetworkManager(
    private val connectivityManager: ConnectivityManager
) {
    private val _state = MutableStateFlow<ConnectionState>(ConnectionState.Unset)
    val state = _state.asStateFlow()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _state.value = ConnectionState.Connected
        }

        override fun onLost(network: Network) {
            if (isConnected()) {
                _state.value = ConnectionState.Connected
            } else {
                _state.value = ConnectionState.Disconnected
            }
        }

        private fun isConnected(): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val activeNetwork = connectivityManager.activeNetwork
                connectivityManager.getNetworkCapabilities(activeNetwork)
                    ?.hasCapability(NET_CAPABILITY_INTERNET) == true
            } else {
                @Suppress("DEPRECATION")
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                @Suppress("DEPRECATION")
                activeNetworkInfo?.isConnected == true
            }
        }
    }

    fun registerNetworkCallback() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NET_CAPABILITY_INTERNET)
            .build()

        try {
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        } catch (e: SecurityException) {
            // Handle exception
            e.printStackTrace()
        }
    }

    fun unregisterNetworkCallback() {
        try {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } catch (ignore: IllegalArgumentException) {
        }
    }
}

sealed interface ConnectionState {
    object Unset : ConnectionState
    object Connected : ConnectionState
    object Disconnected : ConnectionState
}