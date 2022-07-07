package com.example.searchapp.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchapp.R
import com.example.searchapp.domain.ErrorEntity
import com.example.searchapp.domain.Result
import com.example.searchapp.domain.model.info.Info
import com.example.searchapp.domain.use_cases.GetCharacters
import com.example.searchapp.domain.use_cases.GetMoreCharacters
import com.example.searchapp.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

    private var scrollPosition = 0
    private var currentPage = 1

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.EnteredCharacter -> state = state.copy(character = event.value)
            is HomeEvent.GetCharacters -> getCharacter(character = event.value)
        }
    }

    private fun getCharacter(character: String) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            getMoreCharacters(currentPage, character).also { result ->
                when (result) {
                    is Result.Error -> {
                        when (result.error) {
                            ErrorEntity.ApiError.NotFound -> {
                                _eventFlow.emit(UIEvent.ShowSnackBar(
                                    message = UiText.StringResource(R.string.io_exception_error)
                                ))
                            }
                            ErrorEntity.ApiError.UnKnown -> {
                                _eventFlow.emit(UIEvent.ShowSnackBar(
                                    message = UiText.StringResource(R.string.unknown_exception_error)
                                ))
                            }
                            ErrorEntity.InputError.EmailError -> {
                                _eventFlow.emit(UIEvent.ShowSnackBar(
                                    message = UiText.StringResource(R.string.character_error,2)
                                ))
                            }
                        }
                    }
                    is Result.Success -> {
                        getCharactersUseCase(character).onEach {
                            state = state.copy(
                                characters = it,
                            )
                        }.launchIn(this)
                    }
                }
            }
        }
        state = state.copy(isLoading = false)
    }

    private fun nextPage(
        input: String,
    ) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            currentPage++
            delay(1000)
            getMoreCharacters(currentPage, input).also { result ->
                when (result) {
                    is Result.Error -> {
                        when (result.error) {
                            ErrorEntity.ApiError.NotFound -> {
                                _eventFlow.emit(UIEvent.ShowSnackBar(
                                    message = UiText.StringResource(R.string.io_exception_error)
                                ))
                            }
                            ErrorEntity.ApiError.UnKnown -> {
                                _eventFlow.emit(UIEvent.ShowSnackBar(
                                    message = UiText.StringResource(R.string.unknown_exception_error)
                                ))
                            }
                            ErrorEntity.InputError.EmailError -> {
                                _eventFlow.emit(UIEvent.ShowSnackBar(
                                    message = UiText.StringResource(R.string.character_error,2)
                                ))
                            }
                        }
                    }
                    is Result.Success -> {
                        getCharactersUseCase(input).onEach {
                            state = state.copy(
                                characters = it,
                            )
                        }.launchIn(this)
                    }
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    fun requireMoreCharacters(input: String, position: Int) {
        scrollPosition = position
        if ((scrollPosition + 1) >= (UI_PAGE_SIZE * currentPage)) {
            nextPage(input)
        }
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: UiText): UIEvent()
    }
}