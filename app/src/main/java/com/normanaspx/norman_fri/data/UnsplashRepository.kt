package com.normanaspx.norman_fri.data

import androidx.annotation.WorkerThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.normanaspx.norman_fri.api.PhotoDao
import com.normanaspx.norman_fri.api.UnsplashApi
import com.normanaspx.norman_fri.data.models.PhotoEntity
import com.normanaspx.norman_fri.data.models.PhotoWithDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

/**
Creada por Norman el 3/13/2021
 **/

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

    val getPhotos: Flow<List<PhotoWithDetails>> = dao.getPhotos()

    @WorkerThread
    suspend fun insertPhoto(item: PhotoWithDetails) = withContext(Dispatchers.IO){
        dao.insertPhoto(item.photo)
        item.urls?.let { dao.insertUrls(it) }
        item.user?.let { dao.insertUser(it) }

    }

    @WorkerThread
    suspend fun deletePhoto(item: PhotoEntity) = withContext(Dispatchers.IO){
        dao.delete(item)
    }

}