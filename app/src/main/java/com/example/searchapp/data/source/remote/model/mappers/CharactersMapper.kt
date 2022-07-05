package com.example.searchapp.data.source.remote.model.mappers

import com.example.searchapp.data.source.remote.model.ApiCharacters
import com.example.searchapp.domain.model.characters.Character
import javax.inject.Inject

class CharactersMapper @Inject constructor(): Mapper<ApiCharacters?, Character> {
    override fun mapToDomain(apiEntity: ApiCharacters?): Character {
        return Character(
            id = apiEntity?.id ?: throw MappingException("Result ID cannot be null"),
            name = apiEntity.name.orEmpty(),
            status = apiEntity.status.orEmpty(),
            species = apiEntity.species.orEmpty(),
            image = apiEntity.image.orEmpty()
        )
    }
}