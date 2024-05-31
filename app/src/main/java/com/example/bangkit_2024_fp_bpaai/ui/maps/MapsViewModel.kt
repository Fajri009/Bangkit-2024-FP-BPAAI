package com.example.bangkit_2024_fp_bpaai.ui.maps

import androidx.lifecycle.ViewModel
import com.example.bangkit_2024_fp_bpaai.data.StoryRepository

class MapsViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun getStoryWithLocation(token: String, location: Int) =
        storyRepository.getStoriesWithLocation("Bearer $token", location)
}