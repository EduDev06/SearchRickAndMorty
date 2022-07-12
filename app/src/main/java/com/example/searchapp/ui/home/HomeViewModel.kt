package com.example.searchapp.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchapp.R
import com.example.searchapp.util.ErrorEntity
import com.example.searchapp.util.Result
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
    private var totalPages = 41

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.EnteredCharacter -> state = state.copy(input = event.value)
            is HomeEvent.GetCharacters -> getCharacters(input = event.value)
        }
    }

    private fun getCharacters(input: String) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            getMoreCharacters(currentPage, input).also { result ->
                when (result) {
                    is Result.Error -> {
                        when (result.error) {
                            ErrorEntity.ApiError.NotFound -> {
                                getCharactersUseCase(input, UI_PAGE_SIZE, (UI_PAGE_SIZE*(currentPage-1))).map {
                                    state = state.copy(
                                        characters = it,
                                    )
                                }.launchIn(this)
                                _eventFlow.emit(UIEvent.ShowSnackBar(
                                    message = UiText.StringResource(R.string.io_exception_error)
                                ))
                            }
                            ErrorEntity.ApiError.UnKnown -> {
                                getCharactersUseCase(input, UI_PAGE_SIZE, (UI_PAGE_SIZE*(currentPage-1))).map {
                                    state = state.copy(
                                        characters = it,
                                    )
                                }.launchIn(this)
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
                        totalPages = result.data?.pages ?: 41
                        getCharactersUseCase(input, UI_PAGE_SIZE, (UI_PAGE_SIZE * (currentPage-1))).onEach {
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
                                getCharactersUseCase(input, UI_PAGE_SIZE, (UI_PAGE_SIZE * (currentPage-1))).onEach {
                                    state = state.copy(
                                        characters = it,
                                    )
                                }.launchIn(this)
                                _eventFlow.emit(UIEvent.ShowSnackBar(
                                    message = UiText.StringResource(R.string.io_exception_error)
                                ))
                            }
                            ErrorEntity.ApiError.UnKnown -> {
                                getCharactersUseCase(input, UI_PAGE_SIZE, (UI_PAGE_SIZE* (currentPage-1))).onEach {
                                    state = state.copy(
                                        characters = it,
                                    )
                                }.launchIn(this)
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
                        currentPage++
                        getCharactersUseCase(input, UI_PAGE_SIZE, (UI_PAGE_SIZE* (currentPage-1))).onEach {
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
        if ((scrollPosition + 2) >= (totalPages * UI_PAGE_SIZE)) {
            nextPage(input)
        }
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: UiText): UIEvent()
    }
}