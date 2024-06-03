package com.example.bangkit_2024_fp_bpaai.data.local.room.remotemediator

import androidx.room.*

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)