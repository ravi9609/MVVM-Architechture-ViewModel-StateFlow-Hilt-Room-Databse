package com.example.mvvmdemo.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mvvmdemo.model.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Insert
     fun insertPost(channel: List<Post>)

    @Query("SELECT * FROM post")
     fun getAllPost(): List<Post>

    @Query("SELECT * FROM post")
    fun getAllPost1(): Flow<List<Post>>

    @Query("DELETE FROM post ")
     fun deletePost()
}