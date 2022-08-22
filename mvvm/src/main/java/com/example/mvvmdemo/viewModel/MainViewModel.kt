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
//                postStatFlow.value = ApiState.Success(postDao.getAllPost())
            }
        }
//        val setting = async (Dispatchers.IO) {
//            postStatFlow.value = ApiState.Success(apiService1.getPost())
//
//        }
//        setting.await()
//
//        minterface.onCall("ravi")

        mainRepository.getPost().catch { e ->
            postStatFlow.value = ApiState.Failure(e)
        }.collect { data1 ->

            GlobalScope.launch {
                if (data1.isNotEmpty()) {
                    postDao.deletePost()
                    postStatFlow.value = ApiState.Success(data1)
                    postDao.insertPost(data1)

//                    liveData2.postValue(array)
//                    delay(1000)
//                    liveData1.postValue(string1.value)


                }
            }
            minterface.onCall("ravi")

        }
    }


    /*

    fun getPost1(): Flow<List<Post>> = flow {
        emit(apiServiceImpl.getPost())
    }.flowOn(Dispatchers.IO)


    val data:List<Post> = arrayListOf(Post(0,"1"), Post(1,"2"),Post(0,"3"),Post(1,"4"))
    var string1: MutableLiveData<String> = MutableLiveData("Hello")
    var string3: MutableLiveData<String> = MutableLiveData("Hello")
    var string2 = "World!"
    var flowCheck:Flow<String> = flow{
        emit(string1.value.toString())
    }
    var dataFlow = postDao.getAllPost1().flowOn(Dispatchers.IO)
    var dataFlow1 = dataFlow.flatMapConcat {
        flowCheck
    }
    fun dunny(list: List<Post>) {

    }
    val liveData1: MutableLiveData<String> = object : MutableLiveData<String>() {
        override fun onActive() {
            Log.e("liveData1", " : onActive")
        }
        override fun onInactive() {
            Log.e("liveData1", " : onInactive")
        }
    }
    val liveData2: MutableLiveData<ArrayList<String>> = MutableLiveData()
    var array = arrayListOf("a", "c", "b")
    var array1 = arrayListOf("d", "c", "b")
    val liveData3: MutableLiveData<ArrayList<String>> = MutableLiveData(array1)
    var liveDataMerger: LiveData<String> = Transformations.map(liveData1) {
        var list:ArrayList<String> = arrayListOf()
        if(liveData2.value!=null){
            list.addAll(liveData2.value!!)
            list.addAll(liveData2.value!!)
        }
        liveData2.value=array1
        "$it $string2"
    }


    var liveDataMergerArray: LiveData<ArrayList<String>> = Transformations.map(liveData2) { it ->
        val list1: ArrayList<String> = arrayListOf()
        it.forEach {
            list1.add("$it : simple")
        }
        getLivedata(list1)
    }

    var liveDataSwitch: LiveData<ArrayList<String>> = Transformations.switchMap(liveData2) {
        liveData {
            withContext(Dispatchers.IO) {
                emit(getLivedata(it))
            }
        }
    }

//    var liveDataSwitch1: LiveData<ArrayList<String>> = Transformations.switchMap(liveData2) {
//        getLivedata1(it)
//    }

    var liveDataSwitchdistinct: LiveData<ArrayList<String>> = Transformations.distinctUntilChanged(liveData2)

    fun getLivedata(it: ArrayList<String>): ArrayList<String>? {
        Log.e("checkObserver", " : getLiveData")
        liveData3.value?.addAll(it)
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.e("checkObserver", " : main thread")

        } else {
            Log.e("checkObserver", " : background thread")

        }
//        liveData2.postValue(array)
        return liveData3.value
    }

    fun getLivedata1(it: ArrayList<String>): MutableLiveData<ArrayList<String>> {
        Log.e("checkObserver", " : getLiveData1")
        liveData3.value?.addAll(it)
        return liveData3
    }

     */
}