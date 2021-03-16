package com.normanaspx.norman_fri.data.models

import androidx.room.*
import androidx.room.ForeignKey.CASCADE


/**
Creada por Norman el 3/15/2021
 **/

@Entity
data class PhotoEntity(
    @PrimaryKey
    val id: String,
    val description: String?,
    val likes: Int,
    var like: Boolean = false
)


@Entity(tableName = "UrlsEntity")
data class UrlsEntity constructor(
    val raw: String,
    val full: String,
    val regular: String,
    @ForeignKey
        (
        entity = PhotoEntity::class,
        parentColumns = ["id"],
        childColumns = ["id_fkimage"],
        onDelete = CASCADE
    )
    var id_fkimage: String
){
    @PrimaryKey(autoGenerate = true)
    var idUrls: Int = 0
}


@Entity(tableName = "UserEntity")
data class UserEntity(
    @PrimaryKey()
    var idUser: String,
    val name: String,
    val username: String,
    val bio: String = "",
    @ForeignKey
        (
        entity = PhotoEntity::class,
        parentColumns = ["id"],
        childColumns = ["id_fkUser"],
        onDelete = CASCADE
    )
    var id_fkUser: String
)
