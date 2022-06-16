package com.example.searchapp.data.source.remote

import com.example.searchapp.data.source.remote.dto.CharactersDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character/")
    suspend fun getCharacters(
        @Query("page") id: Int,
        @Query("name") name: String
    ): CharactersDto
}