package com.example.bangkit_2024_fp_bpaai.data.remote.retrofit

import com.example.bangkit_2024_fp_bpaai.data.remote.response.AddStoryResponse
import com.example.bangkit_2024_fp_bpaai.data.remote.response.LoginResponse
import com.example.bangkit_2024_fp_bpaai.data.remote.response.RegisterResponse
import com.example.bangkit_2024_fp_bpaai.data.remote.response.Story
import okhttp3.*
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Story

    @GET("stories")
    suspend fun getStoriesWithLocation(
        @Header("Authorization") token: String,
        @Query("location") location: Int = 1
    ): Story

    @Multipart
    @POST("stories")
    suspend fun addStories(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Double?,
        @Part("lon") lon: Double?
    ): AddStoryResponse
}