package com.example.mvvmdemo.network

import com.example.mvvmdemo.di.AppModule
import com.example.mvvmdemo.model.Post
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService1:ApiService1,
                                         @AppModule.AuthInterceptorOkHttpClient
                                         private val apiService:ApiService,
                                         @AppModule.OtherInterceptorOkHttpClient
                                         private val apiServiceOther:ApiService) {

    suspend fun getPost():List<Post> = apiService1.getPost()
}