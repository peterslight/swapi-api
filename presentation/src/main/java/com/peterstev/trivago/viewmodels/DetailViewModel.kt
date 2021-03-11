package com.peterstev.trivago.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterstev.data.repository.DetailRepository
import com.peterstev.domain.models.Film
import com.peterstev.domain.models.Planet
import com.peterstev.domain.models.Specie
import com.peterstev.trivago.utilities.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch


class DetailViewModel @ViewModelInject constructor(private val repository: DetailRepository) :
    ViewModel() {

    private val filmsLiveData = MutableLiveData<Resource<List<Film>>>()
    private val filmListStore: MutableList<Film> = mutableListOf()

    private val speciesLiveData = MutableLiveData<Resource<Specie?>>()
    private val planetLiveData = MutableLiveData<Resource<Planet?>>()

    val filmsObservable: LiveData<Resource<List<Film>>> = filmsLiveData
    val planetObservable: LiveData<Resource<Planet?>> = planetLiveData
    val specieObservable: LiveData<Resource<Specie?>> = speciesLiveData

    fun getFilms(filmList: List<String>) {
        if (filmListStore.isEmpty()) {
            viewModelScope.launch {
                filmsLiveData.postValue(Resource.loading(emptyList()))
                repository.getFilms(filmList).catch { error ->
                    filmsLiveData.postValue(Resource.error(error.message!!, emptyList()))
                }.onCompletion { error ->
                    if (error == null) filmsLiveData.postValue(Resource.success(filmListStore))
                }.collect { film ->
                    filmListStore.add(film)
                }
            }
        }
    }

    fun getSpecie(specieUrl: String) {

        if (specieUrl.isNotEmpty() && speciesLiveData.value?.data == null)
            viewModelScope.launch {
                try {
                    speciesLiveData.postValue(Resource.loading(null))
                    val specie = repository.getSpecie(specieUrl)
                    speciesLiveData.postValue(Resource.success(specie))
                } catch (e: Exception) {
                    speciesLiveData.postValue(Resource.error(e.message!!, null))
                }
            }
    }

    fun getPlanet(planetUrl: String) {
        if (planetUrl.isNotEmpty() && planetLiveData.value?.data == null)
        viewModelScope.launch {
            try {
                planetLiveData.postValue(Resource.loading(null))
                val specie = repository.getPlanet(planetUrl)
                planetLiveData.postValue(Resource.success(specie))
            } catch (e: Exception) {
                planetLiveData.postValue(Resource.error(e.message!!, null))
            }
        }
    }

    fun cleanUp() {
        filmListStore.clear()
        filmsLiveData.postValue(Resource.idle(emptyList()))
        speciesLiveData.postValue(Resource.idle(null))
        planetLiveData.postValue((Resource.idle(null)))
    }

}