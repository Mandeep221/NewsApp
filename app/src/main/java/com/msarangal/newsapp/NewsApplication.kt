package com.msarangal.newsapp

import android.app.Application
import com.msarangal.newsapp.di.AppObjectFactory
import com.msarangal.newsapp.di.AppObjectFactoryImpl
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApplication: Application() {

    companion object {
        lateinit var appObjectFactory: AppObjectFactory
    }
    override fun onCreate() {
        super.onCreate()
        appObjectFactory = AppObjectFactoryImpl(this)
    }
}