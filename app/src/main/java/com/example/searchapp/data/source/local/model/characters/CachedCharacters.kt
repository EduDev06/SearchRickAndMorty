package com.example.searchapp.data.source.local.model.characters

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.searchapp.domain.model.characters.Character
import com.example.searchapp.domain.model.characters.Location
import com.example.searchapp.domain.model.characters.Origin

@Entity(tableName = "characters")
data class CachedCharacters(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    @ColumnInfo(name = "origin_name") val originName: String,
    @ColumnInfo(name = "origin_url") val originUrl: String,
    @ColumnInfo(name = "location_name") val locationName: String,
    @ColumnInfo(name = "location_url") val locationUrl: String,
) {
    companion object {
        fun fromDomain(domainModel: Character): CachedCharacters {
            val location = domainModel.location
            val origin = domainModel.origin
            return CachedCharacters(
                id = domainModel.id,
                name = domainModel.name,
                status = domainModel.status,
                species = domainModel.species,
                image = domainModel.image,
                originName = origin.name,
                originUrl = origin.url,
                locationName = location.name,
                locationUrl = location.url
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
            origin = Origin(
                name = originName,
                url = originUrl
            ),
            location = Location(
                name = locationName,
                url = locationUrl
            )
        )
    }
}