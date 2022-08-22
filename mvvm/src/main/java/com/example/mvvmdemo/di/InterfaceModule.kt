package com.example.mvvmdemo.di

import com.example.mvvmdemo.MainActivity
import com.example.mvvmdemo.network.CheckInterface
import com.example.mvvmdemo.viewModel.CheckingInterfaceCalling
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InterfaceModule {

    @Binds
    @Singleton
    abstract fun locationListener(minterface: CheckingInterfaceCalling): CheckInterface

//    @Binds
//    @Singleton
//    abstract fun locationListener1(mainActivity: MainActivity): CheckInterface


}