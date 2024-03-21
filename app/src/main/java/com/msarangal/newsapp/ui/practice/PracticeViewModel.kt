package com.msarangal.newsapp.ui.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheezycode.notesample.models.UserRequest
import com.msarangal.newsapp.domain.PracticeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PracticeViewModel @Inject constructor(private val practiceRepository: PracticeRepository) :
    ViewModel() {

    val healthsNewsLiveData get() = practiceRepository.fetchNewsFromDb()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            practiceRepository.getNews()
        }
    }

    fun registerUser(userRequest: UserRequest) {
        viewModelScope.launch {
            practiceRepository.registerUser(userRequest)
        }
    }

    fun loginUser(userRequest: UserRequest) {
        viewModelScope.launch {
            practiceRepository.loginUser(userRequest)
        }
    }
}