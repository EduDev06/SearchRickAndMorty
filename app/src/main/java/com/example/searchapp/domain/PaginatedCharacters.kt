package com.example.searchapp.domain

import com.example.searchapp.domain.info.Info
import com.example.searchapp.domain.result.Result

data class PaginatedCharacters(
    val info: Info,
    val results: List<Result>
)
