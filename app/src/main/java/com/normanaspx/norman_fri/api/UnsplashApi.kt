package com.normanaspx.norman_fri.api

import com.normanaspx.norman_fri.FriApplication.Companion.CLIENT_ID
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query



/**
Creada por Norman el 3/13/2021
 **/
interface UnsplashApi {

    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("search/photos")
    suspend fun searchPhotos(@Query("query") query: String,
                             @Query("page") page: Int,
                             @Query("per_page") perPage: Int
    ): ApiResponse

}