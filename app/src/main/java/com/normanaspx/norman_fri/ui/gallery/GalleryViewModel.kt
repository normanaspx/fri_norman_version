package com.normanaspx.norman_fri.ui.gallery

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.normanaspx.norman_fri.data.models.PhotoEntity
import com.normanaspx.norman_fri.data.UnsplashRepository
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
        private const val DEFAULT_QUERY = "cats"
    }

    fun insert(user: PhotoEntity){
        viewModelScope.launch {
            repository.insertPhoto(user)
        }
    }
    override fun onCleared() {
        super.onCleared()
    }

}