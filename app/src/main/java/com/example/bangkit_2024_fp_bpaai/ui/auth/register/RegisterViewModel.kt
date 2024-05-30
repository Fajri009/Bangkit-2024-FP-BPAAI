package com.example.bangkit_2024_fp_bpaai.ui.auth.register

import androidx.lifecycle.ViewModel
import com.example.bangkit_2024_fp_bpaai.data.StoryRepository

class RegisterViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun register(name: String, email: String, password: String) =
        storyRepository.register(name, email, password)
}