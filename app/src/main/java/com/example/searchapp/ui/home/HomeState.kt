package com.example.searchapp.ui.home

import com.example.searchapp.domain.model.characters.Character

data class HomeState(
    val characters: List<Character> = emptyList(),
    val character: String = "",
    val isLoading: Boolean = false,
)
