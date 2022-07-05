package com.example.searchapp.ui.home

sealed class HomeEvent {
    data class EnteredCharacter(val value: String): HomeEvent()
    data class GetCharacters(val value: String): HomeEvent()
    data class NextPage(val value: String): HomeEvent()
}
