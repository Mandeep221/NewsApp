package com.msarangal.newsapp.base

import android.content.Intent
import androidx.activity.ComponentActivity

interface DeepLinkHandler {
    fun handleDeeplink(activity: ComponentActivity, intent: Intent?)
}

class DeeplinkHandlerImpl : DeepLinkHandler {
    override fun handleDeeplink(activity: ComponentActivity, intent: Intent?) {
        // handle
    }

}