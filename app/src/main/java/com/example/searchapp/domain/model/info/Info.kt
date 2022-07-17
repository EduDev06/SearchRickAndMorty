package com.example.searchapp.domain.model.info

data class Info(
    val pages: Int, // Total Pages
) {
    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }
}