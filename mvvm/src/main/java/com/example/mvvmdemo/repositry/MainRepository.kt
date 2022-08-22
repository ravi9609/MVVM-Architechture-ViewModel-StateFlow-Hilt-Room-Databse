package com.example.mvvmdemo.repositry

import com.example.mvvmdemo.model.Post
import com.example.mvvmdemo.network.ApiServiceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiServiceImpl: ApiServiceImpl){

    var list:ArrayList<Post> = arrayListOf()
    fun getPost():Flow<List<Post>> = flow {
        emit(apiServiceImpl.getPost())
    }.flowOn(Dispatchers.IO)



}