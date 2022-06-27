package com.example.searchapp.data.source.local

import com.example.searchapp.data.source.local.daos.CharactersDao
import com.example.searchapp.data.source.local.model.characters.CachedCharacters
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val charactersDao: CharactersDao
): Cache {
    override fun getCharacters(character: String): List<CachedCharacters> {
        return charactersDao.getCharacters(character)
    }

    override suspend fun storeCharacters(characters: List<CachedCharacters>) {
        return charactersDao.insertCharacter(characters)
    }
}