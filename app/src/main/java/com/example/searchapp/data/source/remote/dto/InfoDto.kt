package com.example.searchapp.data.source.remote.dto

import com.example.searchapp.domain.Info

data class InfoDto(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: Any
)

fun InfoDto.toInfo(): Info {
    return Info(
        count = count,
        next = next,
        pages = pages,
        prev = prev
    )
}