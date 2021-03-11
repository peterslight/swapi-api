package com.peterstev.data.repository

import com.peterstev.data.interactors.DetailCaseImpl
import com.peterstev.domain.models.Film
import com.peterstev.domain.models.Planet
import com.peterstev.domain.models.Specie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailRepository @Inject constructor(private val detailCaseImpl: DetailCaseImpl) {
    suspend fun getPlanet(planetUrl: String): Planet = detailCaseImpl.getPlanet(planetUrl)
    suspend fun getSpecie(specieUrl: String): Specie = detailCaseImpl.getSpecie(specieUrl)
    fun getFilms(filmUrlList: List<String>): Flow<Film> = flow {
        return@flow filmUrlList.forEach {
            val film = detailCaseImpl.getFilm(it)
            return@forEach emit(film)
        }
    }
}