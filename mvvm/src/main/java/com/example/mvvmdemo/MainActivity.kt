package com.example.mvvmdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.broadcastReceiver.BroadcastReciverClass
import com.example.mvvmdemo.databinding.ActivityMainBinding
import com.example.mvvmdemo.model.Post
import com.example.mvvmdemo.network.CheckInterface
import com.example.mvvmdemo.room.PostDao
import com.example.mvvmdemo.util.ApiState
import com.example.mvvmdemo.viewModel.CheckingInterfaceCalling
import com.example.mvvmdemo.viewModel.MainViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.content.IntentFilter

import android.content.Intent

import android.app.PendingIntent
import com.example.mvvmdemo.util.AirplaneModeChangeReceiver
import android.net.Uri
import com.google.android.material.button.MaterialButton


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CheckInterface {

    @Inject
    lateinit var postDao: PostDao
    @Inject
    lateinit var checkInterface: CheckingInterfaceCalling
    private lateinit var binding: ActivityMainBinding
    private lateinit var postAdapter: PostAdapter
    private val mainViewModel: MainViewModel by viewModels()
    val airplaneModeChangeReceiver = AirplaneModeChangeReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerview()
        mainViewModel.getPost()

        findViewById<MaterialButton>(R.id.btn).setOnClickListener {
            mainViewModel.getPost()
        }
        Log.e("checkData", "  :  "+Dummy().getData())
        lifecycleScope.launchWhenStarted {
            mainViewModel._postStateFlow.collect { it ->
                when (it) {
                    is ApiState.Loading -> {
                        binding.recyclerview.isVisible = false
                        binding.progressBar.isVisible = true
                    }
                    is ApiState.Failure -> {
                        binding.recyclerview.isVisible = false
                        binding.progressBar.isVisible = false
                        Log.d("main", "onCreate: ${it.msg}")
                    }
                    is ApiState.Success -> {
                        Log.e("Success", "ApiState Success :   "+it.data.size)

                        binding.recyclerview.isVisible = true
                        binding.progressBar.isVisible = false
                        postAdapter.setData(it.data)
//                        GlobalScope.launch {
//                            var result=async {
//                                postDao.deletePost()
//                            }
//                            result.await()
//                            postDao.insertPost(it.data)
//                        }
                    }
                    is ApiState.Empty -> {

                    }
                }
            }
        }

//        mainViewModel.liveDataMerger.observe(this,{
//            Log.e("checkObserver", "liveDataMerger :   $it")
//
//        })
//        mainViewModel.liveData2.observe(this,{
//            Log.e("checkObserver", "liveData2 :   $it")
//
//        })
//        mainViewModel.liveDataMergerArray.observe(this,{
//            Log.e("checkObserver", "liveDataMergerArray :   ${Gson().toJson(it)}")
//
//        })
//        mainViewModel.liveDataSwitch.observe(this,{
//            Log.e("checkObserver", "liveDataSwitch :   ${Gson().toJson(it)}")
//
//        })
//        mainViewModel.liveDataSwitchdistinct.observe(this,{
//            Log.e("checkObserver", "liveDataSwitchdistinct :   ${Gson().toJson(it)}")
//
//        })
//        mainViewModel.liveDataSwitch1.observe(this,{
//            Log.e("checkObserver", "liveDataSwitch1 :   ${Gson().toJson(it)}")
//
//        })
//
//        lifecycleScope.launch {
//            // Trigger the flow and consume its elements using collect
//            mainViewModel.dataFlow .catch { exception ->
//                Log.e("dataFlow", "exception : "+exception)
//            }.collect {
//
//                // Update View with the latest favorite news
//                var list=it as List<Post>
//                Log.e("dataFlow", " : "+list.size)
//
//            }
//        }

    }

    private fun initRecyclerview() {
        postAdapter = PostAdapter(ArrayList())
        binding.recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = postAdapter
        }
    }

    override fun onCall(value: String) {
    }


}