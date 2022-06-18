package com.example.searchapp.data.source.remote.dto

import com.example.searchapp.domain.characters.Result

data class ResultDto(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: LocationDto,
    val name: String,
    val origin: OriginDto,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)

fun ResultDto.toResultDto(): Result {
    return Result(
        id = id,
        name = name,
        status = status,
        species = species,
        image = image
    )
}