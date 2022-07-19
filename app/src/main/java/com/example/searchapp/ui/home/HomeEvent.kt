package com.example.searchapp.ui.home

sealed class HomeEvent {
    data class EnteredCharacter(val value: String): HomeEvent()
}
