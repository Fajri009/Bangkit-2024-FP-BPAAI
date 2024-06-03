package com.example.bangkit_2024_fp_bpaai.data.local.room

import androidx.paging.PagingSource
import androidx.room.*
import com.example.bangkit_2024_fp_bpaai.data.remote.response.ListStoryItem

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<ListStoryItem>)

    @Query("SELECT * FROM story")
    fun getAllStory(): PagingSource<Int, ListStoryItem>

    @Query("DELETE FROM story")
    suspend fun deleteAll()
}