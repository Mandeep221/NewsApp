package com.msarangal.newsapp.util

import android.content.Context
import com.msarangal.newsapp.util.Constants.KEY_TOKEN
import com.msarangal.newsapp.util.Constants.PREF_TOKEN_MANAGER
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext val context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences(PREF_TOKEN_MANAGER, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }
}