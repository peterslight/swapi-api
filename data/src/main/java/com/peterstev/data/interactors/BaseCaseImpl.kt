package com.peterstev.data.interactors

import com.peterstev.data.api.ApiService
import com.peterstev.domain.models.People
import com.peterstev.domain.models.Result
import com.peterstev.domain.usecases.BaseUseCases
import javax.inject.Inject

class BaseCaseImpl @Inject constructor(private val apiService: ApiService) : BaseUseCases {
    override suspend fun searchCharacter(character: String): People =
        apiService.searchCharacters(character)
}