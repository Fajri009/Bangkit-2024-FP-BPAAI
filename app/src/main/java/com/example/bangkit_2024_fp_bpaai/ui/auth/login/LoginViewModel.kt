package com.example.bangkit_2024_fp_bpaai.ui.auth.login

import androidx.lifecycle.ViewModel
import com.example.bangkit_2024_fp_bpaai.data.StoryRepository

class LoginViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun login(email: String, password: String) =
        storyRepository.login(email, password)
}