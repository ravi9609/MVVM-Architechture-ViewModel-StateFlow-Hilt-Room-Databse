package com.example.mvvmdemo.viewModel

import androidx.lifecycle.*
import com.example.mvvmdemo.network.ApiService1
import com.example.mvvmdemo.network.ApiServiceImpl
import com.example.mvvmdemo.network.CheckInterface
import com.example.mvvmdemo.repositry.MainRepository
import com.example.mvvmdemo.room.PostDao
import com.example.mvvmdemo.util.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val minterface: CheckInterface,
    private val postDao: PostDao,
    private val apiServiceImpl: ApiServiceImpl,
    private val apiService1: ApiService1
) : ViewModel() {
    private val postStatFlow: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    val _postStateFlow: StateFlow<ApiState> = postStatFlow.asStateFlow()


    fun getPost() = viewModelScope.launch {
        postStatFlow.value = ApiState.Loading

        GlobalScope.launch {
            if (postDao.getAllPost().isNotEmpty()) {
                postStatFlow.value = ApiState.Success(postDao.getAllPost())
            }
        }

        mainRepository.getPost().catch { e ->
            postStatFlow.value = ApiState.Failure(e)
        }.collect { data1 ->

            GlobalScope.launch {
                if (data1.isNotEmpty()) {
                    postDao.deletePost()
                    postStatFlow.value = ApiState.Success(data1)
                    postDao.insertPost(data1)

                }
            }
            minterface.onCall("ravi")

        }
    }
}