package com.example.searchapp.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class ApiPaginatedCharacters(
    val info: ApiInfo?,
    @SerializedName("results") val characters: List<ApiCharacters>?
)