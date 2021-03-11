package com.peterstev.trivago.mock.utils

import com.google.gson.Gson
import com.peterstev.domain.models.*

class MockGen {

    fun getFilm(): Film {
        return Gson().fromJson(FILM_JSON, Film::class.java)
    }

    fun getPlanet(): Planet {
        return Gson().fromJson(PLANET_JSON, Planet::class.java)
    }

    fun getSpecies(): Specie {
        return Gson().fromJson(SPECIES_JSON, Specie::class.java)
    }

    fun getCharacter(): Result {
        return Gson().fromJson(CHARACTER_JSON, Result::class.java)
    }

    fun getPeople(): People {
        return Gson().fromJson(PEOPLE_JSON, People::class.java)
    }

}