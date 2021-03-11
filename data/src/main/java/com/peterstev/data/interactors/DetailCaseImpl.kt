package com.peterstev.data.interactors

import com.peterstev.data.api.ApiService
import com.peterstev.domain.models.Film
import com.peterstev.domain.models.Planet
import com.peterstev.domain.models.Specie
import com.peterstev.domain.usecases.DetailUseCases
import javax.inject.Inject

class DetailCaseImpl @Inject constructor(private val apiService: ApiService) : DetailUseCases {
    override suspend fun getSpecie(specieUrl: String): Specie = apiService.getSpecie(specieUrl)
    override suspend fun getFilm(filmUrl: String): Film = apiService.getFilm(filmUrl)
    override suspend fun getPlanet(planetUrl: String): Planet = apiService.getPlanet(planetUrl)
}