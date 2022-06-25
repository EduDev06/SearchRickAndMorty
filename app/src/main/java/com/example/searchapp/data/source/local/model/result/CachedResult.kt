package com.example.searchapp.data.source.local.model.result

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.searchapp.domain.model.result.Result

@Entity(tableName = "result")
class CachedResult(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String
) {
    companion object {
        fun fromDomain(domainModel: Result): CachedResult {
            return CachedResult(
                id = domainModel.id,
                name = domainModel.name,
                status = domainModel.status,
                species = domainModel.species,
                image = domainModel.image
            )
        }
    }

    fun toDomain(): Result {
        return Result(
            id = id,
            name = name,
            status = status,
            species = species,
            image = image
        )
    }
}