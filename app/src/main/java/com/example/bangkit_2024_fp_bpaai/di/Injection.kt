package com.example.bangkit_2024_fp_bpaai.di

import com.example.bangkit_2024_fp_bpaai.data.StoryRepository
import com.example.bangkit_2024_fp_bpaai.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(): StoryRepository {
        val apiService = ApiConfig.getApiService()
        return StoryRepository(apiService)
    }
}