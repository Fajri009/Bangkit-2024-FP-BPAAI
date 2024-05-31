package com.example.bangkit_2024_fp_bpaai.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.bangkit_2024_fp_bpaai.data.remote.Result
import com.example.bangkit_2024_fp_bpaai.data.remote.response.AddStoryResponse
import com.example.bangkit_2024_fp_bpaai.data.remote.response.ErrorResponse
import com.example.bangkit_2024_fp_bpaai.data.remote.response.LoginResponse
import com.example.bangkit_2024_fp_bpaai.data.remote.response.RegisterResponse
import com.example.bangkit_2024_fp_bpaai.data.remote.response.Story
import com.example.bangkit_2024_fp_bpaai.data.remote.retrofit.ApiService
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class StoryRepository(
    private val apiService: ApiService
) {
    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<Result<RegisterResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = apiService.register(name, email, password)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.i("errorBody", errorBody!!)
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                val errorMessage = errorResponse.message ?: "Unknown error"
                emit(Result.Error(errorMessage))
            }
        }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = apiService.login(email, password)
                emit(Result.Success(response))
            }  catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.i("errorBody", errorBody!!)
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                val errorMessage = errorResponse.message ?: "Unknown error"
                emit(Result.Error(errorMessage))
            }
        }

    fun getStories(token: String): LiveData<Result<Story>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = apiService.getStories(token)
                emit(Result.Success(response))
            } catch (e: Exception) {
                Log.d("Get Stories", e.message.toString())
                emit(Result.Error(e.message.toString()))
            }
        }

    fun getStoriesWithLocation(token: String, location: Int): LiveData<Result<Story>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = apiService.getStoriesWithLocation(token, location)
                emit(Result.Success(response))
            } catch (e: Exception) {
                Log.d("Get Stories with Location", e.message.toString())
                emit(Result.Error(e.message.toString()))
            }
        }

    fun addStories(
        token: String,
        uri: MultipartBody.Part,
        desc: RequestBody,
        lat: Double?,
        lon: Double?
    ): LiveData<Result<AddStoryResponse>> =
        liveData {
            emit(Result.Loading)

            try {
                val response = apiService.addStories(token, uri, desc, lat, lon)
                emit(Result.Success(response))
            } catch (e: Exception) {
                Log.d("Add Stories", e.message.toString())
                emit(Result.Error(e.message.toString()))
            }
        }
}