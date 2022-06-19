package com.example.searchapp.data.source.remote

import com.example.searchapp.data.source.remote.model.ApiPaginatedCharacters
import retrofit2.http.GET
import retrofit2.http.Query

const val CHARACTER_ENDPOINT = "character/"
const val PAGE_PARAMETER = "page"
const val NAME_PARAMETER = "name"

interface RickAndMortyApi {

    @GET(CHARACTER_ENDPOINT)
    suspend fun getCharacters(
        @Query(PAGE_PARAMETER) id: Int,
        @Query(NAME_PARAMETER) name: String
    ): ApiPaginatedCharacters
}