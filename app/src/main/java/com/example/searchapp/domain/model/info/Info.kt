package com.example.searchapp.domain.model.info

data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String?
) {
    companion object {
        const val DEFAULT_PAGE_SIZE = 41
    }
}
