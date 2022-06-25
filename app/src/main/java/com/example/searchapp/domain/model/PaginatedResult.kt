package com.example.searchapp.domain.model

import com.example.searchapp.domain.model.info.Info
import com.example.searchapp.domain.model.result.Result

/*
We need to paginated all the results from api into Characters object
 */

data class PaginatedResult(
    val info: Info,
    val result: List<Result>
)
