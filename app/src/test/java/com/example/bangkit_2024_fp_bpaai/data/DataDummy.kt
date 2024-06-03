package com.example.bangkit_2024_fp_bpaai.data

import com.example.bangkit_2024_fp_bpaai.data.remote.response.AddStoryResponse
import com.example.bangkit_2024_fp_bpaai.data.remote.response.ListStoryItem
import com.example.bangkit_2024_fp_bpaai.data.remote.response.LoginResponse
import com.example.bangkit_2024_fp_bpaai.data.remote.response.LoginResult
import com.example.bangkit_2024_fp_bpaai.data.remote.response.RegisterResponse

object DataDummy {
    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                i.toString(),
                "created + $i",
                "name + $i",
                "description + $i",
                i.toDouble(),
                "id + $i",
                i.toDouble()
            )
            items.add(story)
        }
        return items
    }

    fun generateDummyLoginResponse(): LoginResponse {
        return LoginResponse(
            LoginResult(
                "name",
                "id",
                "token"
            ),
            false,
            "token"
        )
    }

    fun generateDummyRegisterResponse(): RegisterResponse {
        return RegisterResponse(
            false,
            "success"
        )
    }

    fun generateDummyAddStoryResponse(): AddStoryResponse {
        return AddStoryResponse(
            false,
            "success"
        )
    }
}