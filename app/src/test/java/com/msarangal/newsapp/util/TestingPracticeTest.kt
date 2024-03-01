package com.msarangal.newsapp.util

import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class TestingPracticeTest {

    private lateinit var testingPractice: TestingPractice

    @Before
    fun setup() {
        testingPractice = TestingPractice()
    }

    @Test
    fun validatePassword_empty_string_returnsFalse() {
        val result = testingPractice.validatePassword("")
        assertEquals(false, result)
    }

    @Test
    fun validatePassword_length_notOk_returnsFalse() {
        val result = testingPractice.validatePassword("123")
        assertEquals(false, result)
    }

    @Test
    fun validatePassword_allOk_returnsTrue() {
        val result = testingPractice.validatePassword("QQaa!!22")
        assertEquals(true, result)
    }

    @Test
    fun reverseString_succeeds() {
        val reversed = testingPractice.reverseString("Manu")
        assertEquals("unaM", reversed)
    }

    @Test(expected = IllegalArgumentException::class)
    fun reverseString_throwsException_ifNullIsPassed() {
        val reversed = testingPractice.reverseString(null)
    }
}