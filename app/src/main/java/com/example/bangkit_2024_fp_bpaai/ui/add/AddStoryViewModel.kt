package com.example.bangkit_2024_fp_bpaai.ui.add

import androidx.lifecycle.ViewModel
import com.example.bangkit_2024_fp_bpaai.data.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun addStory(token: String, uri: MultipartBody.Part, desc: RequestBody, lat: Double?, lon: Double?) =
        storyRepository.addStories(token, uri, desc, lat, lon)
}