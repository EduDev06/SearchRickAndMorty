package com.example.searchapp.data.repositories

import com.example.searchapp.data.source.local.Cache
import com.example.searchapp.data.source.remote.RickAndMortyApi
import com.example.searchapp.domain.model.PaginatedCharacters
import com.example.searchapp.domain.repositories.CharacterRepository
import com.example.searchapp.data.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

typealias Characters = com.example.searchapp.domain.model.characters.Characters

class CharacterRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi,
    private val cache: Cache
): CharacterRepository {
    override fun getCharacters(character: String): Flow<Result<List<Characters>>> = flow {
        emit(Result.Loading())
        val response = try {
            cache.getCharacters(character)
                .distinctUntilChanged()
                .map {  }
        } catch (e: HttpException) {
            // return Result.Error(UiText.StringResource(R.string.unknown_exception_error))
        } catch (e: IOException) {
            // return Result.Error(UiText.StringResource(R.string.io_exception_error))
        }
        //emit(Result.Success())
    }

    override suspend fun requestMoreCharacters(
        pageToLoad: Int,
        numbersOfItems: Int
    ): PaginatedCharacters {
        TODO("Not yet implemented")
    }

    override suspend fun storeCharacter(character: Characters) {
        TODO("Not yet implemented")
    }
}