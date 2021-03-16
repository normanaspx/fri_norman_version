package com.normanaspx.norman_fri.data

import androidx.annotation.WorkerThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.normanaspx.norman_fri.api.PhotoDao
import com.normanaspx.norman_fri.api.UnsplashApi
import com.normanaspx.norman_fri.data.models.PhotoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


/**
Creada por Norman el 3/13/2021
 **/

@Singleton
class UnsplashRepository @Inject constructor(private val unsplashApi: UnsplashApi, private val dao: PhotoDao) {

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashPagingSource(unsplashApi, query) }
        ).liveData

    @WorkerThread
    suspend fun insertPhoto(user: PhotoEntity) = withContext(Dispatchers.IO){
        dao.insert(user)
    }


}