package com.msarangal.newsapp.util

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import kotlin.coroutines.CoroutineContext

class TestingPractice {

    private val demo by lazy {
        Demo(
            name = "",
            name2 = "",
            name3 = "",
            name4 = "",
        )
    }

    /**
    1. should be between 6 to 15 characters long
    2. Should not be empty
     */
    fun validatePassword(password: String): Boolean {
        return password.length in 6..15 && password.isNotEmpty()
    }

    fun reverseString(str: String?): String {
        if (str == null) {
            throw IllegalArgumentException("String cannot be null")
        }
        var reversed = ""
        var index = str.length - 1
        while (index >= 0) {
            reversed = "$reversed${str[index]}"
            index--
        }
        return reversed
    }

    fun printSomething() {
        println(CATEGORY)
    }

    companion object {
        const val CATEGORY = "category"
    }
}

data class Demo(
    val name: String,
    val name2: String,
    val name3: String,
    val name4: String,
)

fun main() {
    val bank = Bank()
    bank.updateInterestRate()

    val coroutineScope = CoroutineScope(Dispatchers.IO)

    coroutineScope.launch {
        println("${Bank.getInterestRate()}: Thread name - ${Thread.currentThread().name}")
    }

    println("Thread name - ${Thread.currentThread().name}")
    Thread.sleep(3000)
}