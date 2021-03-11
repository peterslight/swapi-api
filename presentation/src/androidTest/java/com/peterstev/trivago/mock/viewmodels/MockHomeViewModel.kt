package com.peterstev.trivago.mock.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peterstev.domain.models.Result
import com.peterstev.trivago.utilities.Resource
import javax.inject.Inject


class MockHomeViewModel @Inject constructor() :
    ViewModel() {

    private val searchResultLiveData = MutableLiveData<Resource<Result?>>()
    val searchResultObservable: LiveData<Resource<Result?>> = searchResultLiveData

    fun setCharacter(character: Result){
        searchResultLiveData.postValue(Resource.success(character))
    }
}