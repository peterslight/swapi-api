package com.peterstev.trivago.mock.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peterstev.domain.models.Film
import com.peterstev.domain.models.Planet
import com.peterstev.domain.models.Specie
import com.peterstev.trivago.utilities.Resource
import javax.inject.Inject


class MockDetailViewModel @Inject constructor() :
    ViewModel() {

    private val filmsLiveData = MutableLiveData<Resource<List<Film>>>()
    private val filmListStore: MutableList<Film> = mutableListOf()

    private val speciesLiveData = MutableLiveData<Resource<Specie?>>()
    private val planetLiveData = MutableLiveData<Resource<Planet?>>()

    val filmsObservable: LiveData<Resource<List<Film>>> = filmsLiveData
    val planetObservable: LiveData<Resource<Planet?>> = planetLiveData
    val specieObservable: LiveData<Resource<Specie?>> = speciesLiveData

    fun setFilms(film: Film) {
        filmListStore.add(film)
        filmsLiveData.postValue(Resource.success(filmListStore))
    }

    fun setPlanet(planet: Planet) {
        planetLiveData.postValue(Resource.success(planet))
    }

    fun setSpecie(specie: Specie) {
        speciesLiveData.postValue(Resource.success(specie))
    }

    fun cleanUp() {
        filmListStore.clear()
        filmsLiveData.postValue(Resource.idle(emptyList()))
        speciesLiveData.postValue(Resource.idle(null))
        planetLiveData.postValue((Resource.idle(null)))
    }
}