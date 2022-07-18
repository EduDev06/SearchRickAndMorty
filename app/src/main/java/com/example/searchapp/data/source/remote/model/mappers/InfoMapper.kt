package com.example.searchapp.data.source.remote.model.mappers

import com.example.searchapp.data.source.remote.model.ApiInfo
import com.example.searchapp.domain.model.info.Info
import javax.inject.Inject

class InfoMapper @Inject constructor(): Mapper<ApiInfo?, Info> {
    override fun mapToDomain(apiEntity: ApiInfo?): Info {
        return Info(pages = apiEntity?.pages ?: 0)
    }
}