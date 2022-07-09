package com.example.searchapp.data.source.local

import com.example.searchapp.data.source.local.model.characters.CachedCharacters
import kotlinx.coroutines.flow.Flow

interface Cache {

    fun getCharacters(character: String): Flow<List<CachedCharacters>>

    suspend fun storeCharacters(characters: List<CachedCharacters>)
}