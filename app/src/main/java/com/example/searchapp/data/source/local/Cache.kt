package com.example.searchapp.data.source.local

import com.example.searchapp.data.source.local.model.characters.CachedCharacters

interface Cache {

    fun getCharacters(character: String): List<CachedCharacters>

    suspend fun storeCharacters(characters: List<CachedCharacters>)
}