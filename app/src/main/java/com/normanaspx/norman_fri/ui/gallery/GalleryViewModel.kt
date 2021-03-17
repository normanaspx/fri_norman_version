package com.normanaspx.norman_fri.ui.gallery

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.normanaspx.norman_fri.data.UnsplashRepository
import com.normanaspx.norman_fri.data.models.PhotoWithDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class GalleryViewModel @ViewModelInject constructor( private val repository: UnsplashRepository) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val photos = currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = "babies"
    }

    fun insert(user: PhotoWithDetails){
        viewModelScope.launch {
            repository.insertPhoto(user)
        }
    }

    val getUser:LiveData<List<PhotoWithDetails>> get() =
        repository.getPhotos.flowOn(Dispatchers.Main)
            .asLiveData(context = viewModelScope.coroutineContext)


}