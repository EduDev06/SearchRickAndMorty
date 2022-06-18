package com.example.searchapp.domain.characters

data class Result(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String
)