package com.example.searchapp.data.repositories

import com.example.searchapp.data.source.local.Cache
import com.example.searchapp.data.source.remote.RickAndMortyApi
import com.example.searchapp.domain.model.PaginatedCharacters
import com.example.searchapp.domain.repositories.CharacterRepository
import com.example.searchapp.util.Result
import com.example.searchapp.data.source.local.model.characters.CachedCharacters
import com.example.searchapp.data.source.remote.model.mappers.CharactersMapper
import com.example.searchapp.data.source.remote.model.mappers.InfoMapper
import com.example.searchapp.util.ErrorEntity
import com.example.searchapp.domain.model.characters.Character
import com.example.searchapp.domain.model.info.Info
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi,
    private val cache: Cache,
    private val apiInfoMapper: InfoMapper,
    private val apiCharacterMapper: CharactersMapper
): CharacterRepository {
    override fun getCharacters(input: String, limit: Int, offset: Int): Flow<List<Character>> = flow {
        cache.getCharacters(input, limit, offset).map { characterList ->
            characterList.map { it.toDomain() }
        }.collect { emit(it) }
        //emitAll(list)
    }

    override suspend fun requestMoreCharacters(
        pageToLoad: Int,
        name: String
    ): Result<Info> {
        val response = try {
            val (apiInfo, apiCharacters) = api.getCharacters(pageToLoad, name)
            val paginatedCharacters = PaginatedCharacters(
                info = apiInfoMapper.mapToDomain(apiInfo),
                characters = apiCharacters?.map { apiCharacterMapper.mapToDomain(it) }.orEmpty()
            )
            cache.storeCharacters(paginatedCharacters.characters.map { CachedCharacters.fromDomain(it) })
            paginatedCharacters.info
        } catch (e: HttpException) {
            return Result.Error(ErrorEntity.ApiError.UnKnown)
        } catch (e: IOException) {
            return Result.Error(ErrorEntity.ApiError.NotFound)
        }
        return Result.Success(response)
    }
}