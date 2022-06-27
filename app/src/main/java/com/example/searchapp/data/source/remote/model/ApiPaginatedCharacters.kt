package com.example.searchapp.data.source.remote.model

data class ApiPaginatedCharacters(
    val info: ApiInfo?,
    val characters: List<ApiCharacters>?
)