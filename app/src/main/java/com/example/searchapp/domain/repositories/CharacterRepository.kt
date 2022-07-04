package com.example.searchapp.domain.repositories

import com.example.searchapp.domain.Result
import com.example.searchapp.domain.model.PaginatedCharacters
import com.example.searchapp.domain.model.characters.Characters
import com.example.searchapp.domain.model.info.Info
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(character: String): Flow<List<Characters>>
    suspend fun requestMoreCharacters(pageToLoad: Int, name: String): Result<Info>
}