package com.example.searchapp.data.repositories

import com.example.searchapp.data.source.remote.RickAndMortyApi
import com.example.searchapp.domain.model.PaginatedResult
import com.example.searchapp.domain.repositories.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias Characters = com.example.searchapp.domain.model.result.Result

class CharacterRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi
): CharacterRepository {
    override fun getCharacters(): Flow<Result<List<Characters>>> {
        TODO("Not yet implemented")
    }

    override suspend fun requestMoreCharacters(
        pageToLoad: Int,
        numbersOfItems: Int
    ): PaginatedResult {
        TODO("Not yet implemented")
    }

    override suspend fun storeCharacter(character: Characters) {
        TODO("Not yet implemented")
    }
}