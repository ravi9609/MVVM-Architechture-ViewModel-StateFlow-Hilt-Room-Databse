package com.example.mvvmdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mvvmdemo.R
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PracticeFlowActivity : AppCompatActivity() {
    lateinit var flow:Flow<Int>
    lateinit var flowOne: Flow<String>
    lateinit var flowTwo: Flow<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice_flow)
        setupFlow()
        setUpFlow()
        findViewById<MaterialButton>(R.id.button).setOnClickListener {
//            CoroutineScope(Dispatchers.Main).launch{
//                flow.collect {
//                    Log.e("TAG","  $it")
//                }
//            }
            CoroutineScope(Dispatchers.Main).launch {
                flowOne.zip(flowTwo)
                { firstString, secondString ->
                    "$firstString $secondString"
                }.collect {
                    delay(500)
                    Log.e("TAG", it)
                }
            }
        }
    }

    fun setUpFlow(){
        flow = flow {
            Log.e("TAG", "Start flow")

            (1..10).asFlow().onEach {
                kotlinx.coroutines.delay(500)
                Log.e("TAG", "Emitting $it")
                emit(it)
            }
        }.flowOn(Dispatchers.Default)

    }
    private fun setupFlow() {
        var arrayList:Flow<String> = flowOf("hi","rahul","welcome")
        flowOne = arrayList.flowOn(Dispatchers.Default)
        flowTwo = flowOf("Singh", "Shekhar", "Ali").flowOn(Dispatchers.Default)

    }
}