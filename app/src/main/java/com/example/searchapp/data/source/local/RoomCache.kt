package com.example.searchapp.data.source.local

import com.example.searchapp.data.source.local.daos.CharactersDao
import com.example.searchapp.data.source.local.model.characters.CachedCharacters
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val charactersDao: CharactersDao
): Cache {
    override fun getCharacters(character: String, limit: Int, offset: Int): Flow<List<CachedCharacters>> {
        return charactersDao.getCharacters(character, limit, offset)
    }

    override suspend fun storeCharacters(characters: List<CachedCharacters>) {
        return charactersDao.insertCharacter(characters)
    }
}