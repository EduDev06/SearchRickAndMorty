package com.example.searchapp.ui.home

import com.example.searchapp.domain.model.characters.Character

data class HomeState(
    val characters: List<Character> = ArrayList(),
    val input: String = "",
    val isLoading: Boolean = false,
    val page: Int = 1
)