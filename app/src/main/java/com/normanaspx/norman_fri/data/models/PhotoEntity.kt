package com.normanaspx.norman_fri.data.models

import android.os.Parcelable
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import kotlinx.android.parcel.Parcelize


/**
Creada por Norman el 3/15/2021
 **/
@Parcelize
@Entity
data class PhotoEntity(
    @PrimaryKey
    val id: String,
    val description: String?,
    val likes: Int,
    var like: Boolean = false,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val urls: Urls,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val user: User
) : Parcelable {

    @Parcelize
    @Entity(tableName = "Images")
    data class Urls(
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String,
        @ForeignKey
            (entity = Photo::class,
            parentColumns = ["id"],
            childColumns = ["id_fkimage"],
            onDelete = CASCADE
        )
        var id_fkimage: Long
    ) : Parcelable

    @Parcelize
    @Entity()
    data class User(
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        val name: String,
        val username: String,
        val bio: String,
        val instagram_username: String,
        val twitter_username: String,
        @ForeignKey
            (entity = Photo::class,
            parentColumns = ["id"],
            childColumns = ["id_fkUser"],
            onDelete = CASCADE
        )
        var id_fkUser: Long
    ) : Parcelable {
        val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
    }
}