package com.example.mvvmdemo.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvmdemo.model.Post

@Database(entities = [Post::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}