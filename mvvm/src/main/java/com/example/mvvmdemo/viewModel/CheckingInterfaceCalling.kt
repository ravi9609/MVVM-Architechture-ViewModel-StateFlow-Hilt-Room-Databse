package com.example.mvvmdemo.viewModel

import android.util.Log
import com.example.mvvmdemo.network.CheckInterface
import javax.inject.Inject

class CheckingInterfaceCalling @Inject constructor():CheckInterface {

    override fun onCall(value: String) {
        Log.e("checkValue","  interface: $value")

    }

//    fun callInterfaceMethod(){
//        checkInterface.onCall("ravi")
//    }
}