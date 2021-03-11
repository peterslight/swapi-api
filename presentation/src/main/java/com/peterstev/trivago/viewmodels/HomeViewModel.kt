package com.peterstev.trivago.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterstev.data.repository.SearchRepository
import com.peterstev.domain.models.People
import com.peterstev.trivago.utilities.Resource
import kotlinx.coroutines.launch


class HomeViewModel @ViewModelInject constructor(private val repository: SearchRepository) :
    ViewModel() {

    private val searchResultLiveData = MutableLiveData<Resource<People?>>()
    val searchResultObservable: LiveData<Resource<People?>> = searchResultLiveData

    init {
        searchResultLiveData.postValue(Resource.idle(null))
    }

    fun searchCharacter(character: String) {
        viewModelScope.launch {
            try {
                searchResultLiveData.postValue(Resource.loading(null))
                val user = repository.searchCharacter(character)
                searchResultLiveData.postValue(Resource.success(user))
            } catch (e: Exception) {
                searchResultLiveData.postValue(Resource.error(e.message!!, null))
            }
        }
    }
}