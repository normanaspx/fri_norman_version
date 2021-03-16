package com.normanaspx.norman_fri.api

import androidx.room.*
import com.normanaspx.norman_fri.data.models.PhotoEntity

@Dao
interface PhotoDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: PhotoEntity)

  //  @Query("SELECT * FROM Photo")
    //fun search(search: String): List<Contacts.Photos>

    @Delete
    suspend fun delete(photo: PhotoEntity)

}