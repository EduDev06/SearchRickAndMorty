package com.example.searchapp.domain.model.info

data class Info(
    val count: Int, // Characters
    val next: String, // Next Page
    val pages: Int, // Total Pages
    val prev: String? // Previous pages
) {
    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }
}
