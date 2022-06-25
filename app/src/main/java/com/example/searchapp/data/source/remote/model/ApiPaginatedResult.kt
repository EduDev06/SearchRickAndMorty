package com.example.searchapp.data.source.remote.model

data class ApiPaginatedResult(
    val info: ApiInfo?,
    val results: List<ApiResult>?
)