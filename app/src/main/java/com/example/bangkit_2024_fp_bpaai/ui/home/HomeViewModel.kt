package com.example.bangkit_2024_fp_bpaai.ui.home

import androidx.lifecycle.ViewModel
import com.example.bangkit_2024_fp_bpaai.data.StoryRepository

class HomeViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun getStory(token: String) =
        storyRepository.getStories("Bearer $token")
}