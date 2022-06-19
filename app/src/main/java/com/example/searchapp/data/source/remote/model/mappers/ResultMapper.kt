package com.example.searchapp.data.source.remote.model.mappers

import com.example.searchapp.data.source.remote.model.ApiResult
import com.example.searchapp.domain.result.Result
import javax.inject.Inject

class ResultMapper @Inject constructor(): Mapper<ApiResult?, Result> {
    override fun mapToDomain(apiEntity: ApiResult?): Result {
        return Result(
            id = apiEntity?.id ?: throw MappingException("Result ID cannot be null"),
            name = apiEntity.name.orEmpty(),
            status = apiEntity.status.orEmpty(),
            species = apiEntity.species.orEmpty(),
            image = apiEntity.image.orEmpty()
        )
    }
}