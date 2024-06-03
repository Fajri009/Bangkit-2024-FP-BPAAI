package com.example.bangkit_2024_fp_bpaai.di

import android.content.Context
import com.example.bangkit_2024_fp_bpaai.data.StoryRepository
import com.example.bangkit_2024_fp_bpaai.data.local.room.StoryDatabase
import com.example.bangkit_2024_fp_bpaai.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository(database, apiService)
    }
}