package com.example.searchapp.data.source.remote.model.mappers

import com.example.searchapp.data.source.remote.model.ApiLocation
import com.example.searchapp.domain.model.result.Location
import javax.inject.Inject

class LocationMapper @Inject constructor(): Mapper<ApiLocation?, Location> {
    override fun mapToDomain(apiEntity: ApiLocation?): Location {
        return Location(
            name = apiEntity?.name.orEmpty(),
            url = apiEntity?.url.orEmpty()
        )
    }
}