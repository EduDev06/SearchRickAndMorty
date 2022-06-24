package com.example.searchapp.domain.repositories

import com.example.searchapp.data.Result
import com.example.searchapp.domain.model.characters.Characters
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(): Flow<Result<List<Characters>>>
}