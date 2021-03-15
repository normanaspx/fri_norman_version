package com.normanaspx.norman_fri

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/**
Creada por Norman el 3/13/2021
 **/
@HiltAndroidApp
class FriApplication : Application() {

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        const val CLIENT_ID = BuildConfig.UNSPLASH_ACCESS_KEY
    }

}