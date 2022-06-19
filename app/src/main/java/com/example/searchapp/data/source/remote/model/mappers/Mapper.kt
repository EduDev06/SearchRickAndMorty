package com.example.searchapp.data.source.remote.model.mappers

interface Mapper<E, D> {

    fun mapToDomain(apiEntity: E): D
}