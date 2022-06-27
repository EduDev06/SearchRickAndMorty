package com.example.searchapp.data.repositories

import com.example.searchapp.R
import com.example.searchapp.data.source.local.Cache
import com.example.searchapp.data.source.remote.RickAndMortyApi
import com.example.searchapp.domain.model.PaginatedCharacters
import com.example.searchapp.domain.repositories.CharacterRepository
import com.example.searchapp.data.Result
import com.example.searchapp.data.source.local.model.characters.CachedCharacters
import com.example.searchapp.data.source.remote.model.mappers.CharactersMapper
import com.example.searchapp.data.source.remote.model.mappers.InfoMapper
import com.example.searchapp.domain.model.characters.Characters
import com.example.searchapp.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi,
    private val cache: Cache,
    private val apiInfoMapper: InfoMapper,
    private val apiCharacterMapper: CharactersMapper
): CharacterRepository {
    override fun getCharacters(character: String): Flow<Result<List<Characters>>> = flow {
        emit(Result.Loading())
        val response = cache.getCharacters(character).map { it.toDomain() }
        emit(Result.Loading(data = response))
    }

    override suspend fun requestMoreCharacters(
        pageToLoad: Int,
        name: String
    ): Result<PaginatedCharacters> {
        val response = try {
            val (apiInfo, apiCharacters) = api.getCharacters(pageToLoad, name)
            PaginatedCharacters(
                info = apiInfoMapper.mapToDomain(apiInfo),
                characters = apiCharacters?.map { apiCharacterMapper.mapToDomain(it) }.orEmpty()
            )
        } catch (e: HttpException) {
            return Result.Error(
                message = UiText.StringResource(R.string.unknown_exception_error)
            )
        } catch (e: IOException) {
            return Result.Error(
                message = UiText.StringResource(R.string.io_exception_error)
            )
        }
        return Result.Success(data = response)
    }

    override suspend fun storeCharacter(character: List<Characters>) {
        cache.storeCharacters(character.map { CachedCharacters.fromDomain(it) })
    }
}