package com.normanaspx.norman_fri.data.models

import androidx.paging.PagingSource
import com.normanaspx.norman_fri.api.PhotoDao
import com.normanaspx.norman_fri.api.UnsplashApi
import com.normanaspx.norman_fri.data.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


/**
Creada por Norman el 3/13/2021
 **/
private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class FavsPagingSource @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val query: String, private val lsphotos: Flow<List<PhotoEntity>>
) : PagingSource<Int, PhotoEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoEntity> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {

            val flowOfLists: Flow<List<PhotoEntity>> = lsphotos
            val photos: List<PhotoEntity> = flowOfLists.flattenToList()
            
            LoadResult.Page(
                data = photos,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    suspend fun <T> Flow<List<T>>.flattenToList() =
        flatMapConcat { it.asFlow() }.toList()
}