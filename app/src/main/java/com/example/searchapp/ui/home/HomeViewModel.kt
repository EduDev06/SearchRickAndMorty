package com.example.searchapp.ui.home

import androidx.compose .runtime.mutableStateOf
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

    private var searchJob: Job? = null
    private var totalPages = 1

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.EnteredCharacter -> state = state.copy(input = event.value)
        }
    }

    fun getCharacters(input: String, press: Boolean = false) {
        searchJob?.cancel()
        if (press) {
            resetSearchState()
            getCharacters(input)
        } else {
            if ((totalPages > 1) && (state.page < totalPages)) {
                state = state.copy(page = state.page + 1)
                getCharacters(input)
            }
        }
    }

    private fun getCharacters(input: String) {
        searchJob = viewModelScope.launch {
            delay(1000L)
            state = state.copy(isLoading = true)
            getMoreCharacters(state.page, input).also { result ->
                when (result) {
                    is Result.Error -> {
                        when (result.error) {
                            ErrorEntity.ApiError.NotFound -> {
                                _eventFlow.emit(UIEvent.ShowSnackBar(
                                    message = UiText.StringResource(R.string.io_exception_error)
                                ))
                                getCharactersUseCase(input, UI_PAGE_SIZE, (UI_PAGE_SIZE*(state.page-1))).collect {
                                    state = state.copy(
                                        characters = it,
                                        isLoading = false
                                    )
                                }
                            }
                            ErrorEntity.ApiError.UnKnown -> {
                                _eventFlow.emit(UIEvent.ShowSnackBar(
                                    message = UiText.StringResource(R.string.unknown_exception_error)
                                ))
                                getCharactersUseCase(input, UI_PAGE_SIZE, (UI_PAGE_SIZE*(state.page-1))).collect {
                                    state = state.copy(
                                        characters = it,
                                        isLoading = false
                                    )
                                }
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
                        getCharactersUseCase(input, UI_PAGE_SIZE, (UI_PAGE_SIZE*(state.page-1))).collect {
                            appendCharacters(it, state.characters)
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
        state = state.copy(characters = ArrayList(), page = 1)
        totalPages = 1
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: UiText): UIEvent()
    }
}