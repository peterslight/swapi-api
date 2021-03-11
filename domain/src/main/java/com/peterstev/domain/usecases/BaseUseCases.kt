package com.peterstev.domain.usecases

import com.peterstev.domain.models.People

interface BaseUseCases {
    suspend fun searchCharacter(character: String): People
}