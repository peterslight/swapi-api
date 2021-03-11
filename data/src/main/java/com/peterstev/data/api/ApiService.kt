package com.peterstev.data.api

import com.peterstev.domain.models.Film
import com.peterstev.domain.models.People
import com.peterstev.domain.models.Planet
import com.peterstev.domain.models.Specie
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET("people/")
    suspend fun searchCharacters(@Query("search") query: String): People

    @GET
    suspend fun getSpecie(@Url specieUrl: String): Specie

    @GET
    suspend fun getFilm(@Url filmUrl: String): Film

    @GET
    suspend fun getPlanet(@Url planetUrl: String): Planet
}