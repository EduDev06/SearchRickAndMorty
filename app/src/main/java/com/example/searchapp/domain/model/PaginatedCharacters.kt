package com.example.searchapp.domain.model

import com.example.searchapp.domain.model.info.Info
import com.example.searchapp.domain.model.characters.Characters

/*
We need to paginated all the results from api into Characters object
 */

data class PaginatedCharacters(
    val info: Info,
    val characters: List<Characters>
)
