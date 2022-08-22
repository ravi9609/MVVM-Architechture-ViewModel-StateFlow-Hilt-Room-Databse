package com.example.mvvmdemo.di

import android.content.Context
import androidx.room.Room
import com.example.mvvmdemo.model.Post
import com.example.mvvmdemo.network.ApiService
import com.example.mvvmdemo.network.ApiService1
import com.example.mvvmdemo.network.CheckInterface
import com.example.mvvmdemo.room.AppDatabase
import com.example.mvvmdemo.room.PostDao
import com.example.mvvmdemo.viewModel.CheckingInterfaceCalling
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthInterceptorOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class OtherInterceptorOkHttpClient

    @Provides
    fun provideChannelDao(appDatabase: AppDatabase): PostDao {
        return appDatabase.postDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "RssReader"
        ).build()
    }

    @AuthInterceptorOkHttpClient
    @Provides
    @Singleton
    fun provideApiService(@Named("two") url: String): ApiService =
        Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    @OtherInterceptorOkHttpClient
    @Provides
    @Singleton
    fun provideApiServiceother(@Named("two") url: String): ApiService =
        Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiService1(@Named("one") url: String): ApiService1 =
        Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService1::class.java)

    @Provides
    @Named("one")
    fun providesrl() = "https://jsonplaceholder.typicode.com/"

    @Provides
    @Named("two")
    fun providesUrl1() = "https://jsonplaceholder.typicode.com/"


}