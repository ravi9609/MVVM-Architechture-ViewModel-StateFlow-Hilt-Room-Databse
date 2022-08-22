package com.example.mvvmdemo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class PostData (@PrimaryKey(autoGenerate = true) var id: Int, var data:String)