package com.normanaspx.norman_fri.data

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize



@Parcelize
data class Photo(
    val id: String,
    val description: String?,
    val likes: Int,
    var like: Boolean = false,
    val urls: Urls,
    val user: User
) : Parcelable {
    @Parcelize
    data class Urls(
        val raw: String?,
        val full: String?,
        val regular: String?,
        val small: String,
        val thumb: String,
        val image: Bitmap
    ) : Parcelable
    @Parcelize
    data class User(
        val id: String,
        val name: String,
        val username: String,
        val bio: String = "",
        val instagram_username: String,
        val twitter_username: String
    ) : Parcelable {
        val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
    }
}