package com.normanaspx.norman_fri.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.normanaspx.norman_fri.api.PhotoDao
import com.normanaspx.norman_fri.data.models.PhotoEntity


/**
Creada por Norman el 3/15/2021
 **/
@Database(entities = [PhotoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): PhotoDao

}