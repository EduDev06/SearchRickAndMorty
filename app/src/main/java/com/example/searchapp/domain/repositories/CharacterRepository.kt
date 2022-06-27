package com.example.searchapp.domain.repositories

import com.example.searchapp.data.Result
import com.example.searchapp.domain.model.PaginatedCharacters
import com.example.searchapp.domain.model.characters.Characters
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(character: String): Flow<Result<List<Characters>>>
    suspend fun requestMoreCharacters(pageToLoad: Int, name: String): Result<PaginatedCharacters>
    suspend fun storeCharacter(character: List<Characters>)
}