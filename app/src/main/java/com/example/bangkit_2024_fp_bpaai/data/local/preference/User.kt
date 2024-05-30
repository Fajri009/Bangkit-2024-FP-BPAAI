package com.example.bangkit_2024_fp_bpaai.data.local.preference

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var token: String? = ""
): Parcelable