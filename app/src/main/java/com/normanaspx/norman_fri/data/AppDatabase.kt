package com.normanaspx.norman_fri.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.normanaspx.norman_fri.api.PhotoDao
import com.normanaspx.norman_fri.data.models.*

/**
Creada por Norman el 3/15/2021
 **/
@Database(entities = [PhotoEntity::class, UserEntity::class,
    UrlsEntity::class], version =7, exportSchema = false)
@TypeConverters(Converters::class)
//@Database(entities = [PhotoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao

}