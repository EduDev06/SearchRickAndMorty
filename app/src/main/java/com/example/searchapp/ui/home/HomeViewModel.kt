package com.example.searchapp.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchapp.R
import com.example.searchapp.domain.model.characters.Character
import com.example.searchapp.util.ErrorEntity
import com.example.searchapp.util.Result
import com.example.searchapp.domain.model.info.Info
import com.example.searchapp.domain.use_cases.GetCharacters
import com.example.searchapp.domain.use_cases.GetMoreCharacters
import com.example.searchapp.ui.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharacters,
    private val getMoreCharacters: GetMoreCharacters
): ViewModel() {

    companion object {
        const val UI_PAGE_SIZE = Info.DEFAULT_PAGE_SIZE
    }

    var state by mutableStateOf(HomeState())
        private set

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null
    private var currentPage = 1
    private var totalPages = 1
    private var scrollPosition = 0

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.EnteredCharacter -> state = state.copy(input = event.value)
            is HomeEvent.GetCharacters -> getCharacters(input = event.value)
        }
    }

    private fun getCharacters(input: String) {
        resetSearchState()
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            state = state.copy(isLoading = true)
            getMoreCharacters(currentPage, input).also { result ->
                when (result) {
                    is Result.Error -> {
                        when (result.error) {
                            ErrorEntity.ApiError.NotFound -> {
                                getCharactersUseCase(input, UI_PAGE_SIZE, (UI_PAGE_SIZE*(currentPage-1))).collect {
                                    state = state.copy(
                                        characters = it,
                                        isLoading = false
                                    )
                                }
                                _eventFlow.emit(UIEvent.ShowSnackBar(
                                    message = UiText.StringResource(R.string.io_exception_error)
                                ))
                            }
                            ErrorEntity.ApiError.UnKnown -> {
                                getCharactersUseCase(input, UI_PAGE_SIZE, (UI_PAGE_SIZE*(currentPage-1))).collect {
                                    state = state.copy(
                                        characters = it,
                                        isLoading = false
                                    )
                                }
                                _eventFlow.emit(UIEvent.ShowSnackBar(
                                    message = UiText.StringResource(R.string.unknown_exception_error)
                                ))
                            }
                            ErrorEntity.InputError.NameError -> {
                                _eventFlow.emit(UIEvent.ShowSnackBar(
                                    message = UiText.StringResource(R.string.character_error,2)
                                ))
                            }
                        }
                    }
                    is Result.Success -> {
                        totalPages = result.data?.pages ?: 41
                        getCharactersUseCase(input, UI_PAGE_SIZE, (UI_PAGE_SIZE*(currentPage-1))).collect {
                            state = state.copy(
                                characters = it,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun nextPage(input: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            currentPage++
            getMoreCharacters(currentPage, input).also { result ->
                when (result) {
                    is Result.Error -> {
                        when (result.error) {
                            ErrorEntity.ApiError.NotFound -> {
                                getCharactersUseCase(input, UI_PAGE_SIZE, (UI_PAGE_SIZE * (currentPage-1))).collect {
                                    state = state.copy(
                                        characters = it,
                                        isLoading = false
                                    )
                                }
                                _eventFlow.emit(UIEvent.ShowSnackBar(
                                    message = UiText.StringResource(R.string.io_exception_error)
                                ))
                            }
                            ErrorEntity.ApiError.UnKnown -> {
                                getCharactersUseCase(input, UI_PAGE_SIZE, (UI_PAGE_SIZE* (currentPage-1))).collect {
                                    state = state.copy(
                                        characters = it,
                                        isLoading = false
                                    )
                                }
                                _eventFlow.emit(UIEvent.ShowSnackBar(
                                    message = UiText.StringResource(R.string.unknown_exception_error)
                                ))
                            }
                            ErrorEntity.InputError.NameError -> {
                                _eventFlow.emit(UIEvent.ShowSnackBar(
                                    message = UiText.StringResource(R.string.character_error,2),
                                ))
                                state = state.copy(
                                    isLoading = false
                                )
                            }
                        }
                    }
                    is Result.Success -> {
                        val oldCharacters = state.characters
                        getCharactersUseCase(input, UI_PAGE_SIZE, (UI_PAGE_SIZE* (currentPage-1))).collect { newCharacters ->
                            appendCharacters(newCharacters, oldCharacters)
                        }
                    }
                }
            }
        }
    }

    private fun appendCharacters(newCharacters: List<Character>, oldCharacters: List<Character>) {
        val current = ArrayList(oldCharacters)
        current.addAll(newCharacters)
        state = state.copy(characters = current, isLoading = false)
    }

    private fun resetSearchState() {
        state = state.copy(
            characters = ArrayList()
        )
        currentPage = 1
        scrollPosition = 0
    }

    fun requireMoreCharacters(input: String, index: Int) {
        scrollPosition = index
        if ((currentPage < totalPages) && (scrollPosition + 1 >= UI_PAGE_SIZE*currentPage)) {
            nextPage(input)
        }
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: UiText): UIEvent()
    }
}