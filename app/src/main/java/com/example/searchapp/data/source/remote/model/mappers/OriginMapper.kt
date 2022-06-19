package com.example.searchapp.data.source.remote.model.mappers

import com.example.searchapp.data.source.remote.model.ApiOrigin
import com.example.searchapp.domain.result.Origin
import javax.inject.Inject

class OriginMapper @Inject constructor(): Mapper<ApiOrigin?, Origin>{
    override fun mapToDomain(apiEntity: ApiOrigin?): Origin {
        return Origin(
            name = apiEntity?.name.orEmpty(),
            url = apiEntity?.url.orEmpty()
        )
    }
}