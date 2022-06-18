package com.example.searchapp.data.source.remote.dto

import com.example.searchapp.domain.characters.Characters
import com.example.searchapp.domain.characters.Result

data class CharactersDto(
    val info: InfoDto,
    val results: List<ResultDto>
)

fun CharactersDto.toCharacters(): Characters {
    val resultEntries = results.mapIndexed { _, resultDto ->
        Result(
            id = resultDto.id,
            name =  resultDto.name,
            status = resultDto.status,
            species = resultDto.species,
            image = resultDto.image
        )
    }
    return Characters(
        info = info.toInfo(),
        result = resultEntries
    )
}