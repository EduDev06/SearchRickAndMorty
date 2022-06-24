package com.example.searchapp.data.source.remote.model

data class ApiPaginatedCharacters(
    val info: ApiInfo?,
    val results: List<ApiCharacters>?
)