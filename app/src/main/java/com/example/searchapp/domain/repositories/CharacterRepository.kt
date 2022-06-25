package com.example.searchapp.domain.repositories

import com.example.searchapp.domain.model.PaginatedResult
import kotlinx.coroutines.flow.Flow

typealias Characters = com.example.searchapp.domain.model.result.Result
typealias PaginatedCharacters = PaginatedResult

interface CharacterRepository {
    fun getCharacters(): Flow<Result<List<Characters>>>
    suspend fun requestMoreCharacters(pageToLoad: Int, numbersOfItems: Int): PaginatedCharacters
    suspend fun storeCharacter(character: Characters)
}