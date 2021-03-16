package com.normanaspx.norman_fri.api

import androidx.room.*
import com.normanaspx.norman_fri.data.Photo
import com.normanaspx.norman_fri.data.models.PhotoEntity
import com.normanaspx.norman_fri.data.models.PhotoWithDetails
import com.normanaspx.norman_fri.data.models.UrlsEntity
import com.normanaspx.norman_fri.data.models.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertPhoto(photo: PhotoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUrls(photo: UrlsEntity)

    @Delete
    suspend fun delete(photo: PhotoEntity)

    @Query("SELECT * FROM PhotoEntity")
    fun getPhotos(): Flow<List<PhotoEntity>>

}