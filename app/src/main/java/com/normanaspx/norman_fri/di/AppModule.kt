package com.normanaspx.norman_fri.di

import android.content.Context
import androidx.annotation.Nullable
import androidx.room.Room
import com.normanaspx.norman_fri.FriApplication
import com.normanaspx.norman_fri.api.PhotoDao
import com.normanaspx.norman_fri.api.UnsplashApi
import com.normanaspx.norman_fri.data.AppDatabase
import com.normanaspx.norman_fri.data.UnsplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent ::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(FriApplication.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUnsplashApi(retrofit: Retrofit): UnsplashApi =
        retrofit.create(UnsplashApi::class.java)



    @Provides
    fun providesUserDao(appDatabase: AppDatabase):PhotoDao = appDatabase.photoDao()

    @Provides
    @Singleton
    fun providesUserDatabase(@ApplicationContext context: Context):AppDatabase
            = Room.databaseBuilder(context, AppDatabase::class.java,"UserDatabase").build()


    @Provides
    @Nullable
    fun providesUserRepository2(r: UnsplashApi, userDao: PhotoDao) : UnsplashRepository
            = UnsplashRepository(r, userDao)



}