package com.example.searchapp.data.source.local

import com.example.searchapp.data.Result
import com.example.searchapp.data.source.local.model.characters.CachedCharacters
import kotlinx.coroutines.flow.Flow

interface Cache {

    fun getCharacters(character: String): Flow<Result<List<CachedCharacters>>>

    suspend fun storeCharacters(characters: List<CachedCharacters>)
}