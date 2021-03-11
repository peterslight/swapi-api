package com.peterstev.data.repository

import com.peterstev.data.interactors.BaseCaseImpl
import com.peterstev.domain.models.People
import javax.inject.Inject

class SearchRepository @Inject constructor(private val baseCaseImpl: BaseCaseImpl) {
    suspend fun searchCharacter(character: String): People = baseCaseImpl.searchCharacter(character)
}