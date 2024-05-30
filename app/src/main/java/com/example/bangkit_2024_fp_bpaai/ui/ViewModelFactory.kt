package com.example.bangkit_2024_fp_bpaai.ui

import android.content.Context
import androidx.lifecycle.*
import com.example.bangkit_2024_fp_bpaai.data.StoryRepository
import com.example.bangkit_2024_fp_bpaai.di.Injection
import com.example.bangkit_2024_fp_bpaai.ui.add.AddStoryViewModel
import com.example.bangkit_2024_fp_bpaai.ui.auth.login.LoginViewModel
import com.example.bangkit_2024_fp_bpaai.ui.auth.register.RegisterViewModel
import com.example.bangkit_2024_fp_bpaai.ui.home.HomeViewModel

class ViewModelFactory private constructor(private val storyRepository: StoryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                return RegisterViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                return HomeViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
                return AddStoryViewModel(storyRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository())
            }.also { instance = it }
    }
}