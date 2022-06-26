package com.example.searchapp.domain.repositories

import com.example.searchapp.domain.model.PaginatedCharacters
import com.example.searchapp.domain.model.characters.Characters
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(): Flow<Result<List<Characters>>>
    suspend fun requestMoreCharacters(pageToLoad: Int, numbersOfItems: Int): PaginatedCharacters
    suspend fun storeCharacter(character: Characters)
}