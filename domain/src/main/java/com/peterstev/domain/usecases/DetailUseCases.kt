package com.peterstev.domain.usecases

import com.peterstev.domain.models.Film
import com.peterstev.domain.models.Planet
import com.peterstev.domain.models.Specie

interface DetailUseCases {
    suspend fun getSpecie(specieUrl: String): Specie
    suspend fun getFilm(filmUrl: String): Film
    suspend fun getPlanet(planetUrl: String): Planet
}