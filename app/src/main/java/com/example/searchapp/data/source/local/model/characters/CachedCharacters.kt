package com.example.searchapp.data.source.local.model.characters

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.searchapp.domain.model.characters.Characters

@Entity(tableName = "characters")
class CachedCharacters(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String
) {
    companion object {
        fun fromDomain(domainModel: Characters): CachedCharacters {
            return CachedCharacters(
                id = domainModel.id,
                name = domainModel.name,
                status = domainModel.status,
                species = domainModel.species,
                image = domainModel.image
            )
        }
    }

    fun toDomain(): Characters {
        return Characters(
            id = id,
            name = name,
            status = status,
            species = species,
            image = image
        )
    }
}