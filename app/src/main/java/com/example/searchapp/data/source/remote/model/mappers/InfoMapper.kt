package com.example.searchapp.data.source.remote.model.mappers

import com.example.searchapp.data.source.remote.model.ApiInfo
import com.example.searchapp.domain.info.Info
import javax.inject.Inject

class InfoMapper @Inject constructor(): Mapper<ApiInfo?, Info> {
    override fun mapToDomain(apiEntity: ApiInfo?): Info {
        return Info(
            count = apiEntity?.count ?: 0,
            next = apiEntity?.next.orEmpty(),
            pages = apiEntity?.pages ?: 0,
            prev = apiEntity?.prev
        )
    }
}