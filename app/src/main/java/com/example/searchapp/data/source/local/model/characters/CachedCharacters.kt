package com.example.searchapp.data.source.local.model.characters

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.searchapp.domain.model.characters.Character
import com.example.searchapp.domain.model.characters.Location
import com.example.searchapp.domain.model.characters.Origin

@Entity(tableName = "characters")
class CachedCharacters(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val origin: Origin,
    val location: Location
) {
    companion object {
        fun fromDomain(domainModel: Character): CachedCharacters {
            return CachedCharacters(
                id = domainModel.id,
                name = domainModel.name,
                status = domainModel.status,
                species = domainModel.species,
                image = domainModel.image,
                origin = domainModel.origin,
                location = domainModel.location
            )
        }
    }

    fun toDomain(): Character {
        return Character(
            id = id,
            name = name,
            status = status,
            species = species,
            image = image,
            origin = origin,
            location = location
        )
    }
}