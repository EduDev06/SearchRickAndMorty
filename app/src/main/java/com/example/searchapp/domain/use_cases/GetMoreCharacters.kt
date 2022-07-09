package com.example.searchapp.domain.use_cases

import com.example.searchapp.domain.ErrorEntity
import com.example.searchapp.domain.Result
import com.example.searchapp.domain.repositories.CharacterRepository
import javax.inject.Inject

const val MIN_NAME_LENGTH = 2

class GetMoreCharacters @Inject constructor(
    private val repository: CharacterRepository,
) {
    suspend operator fun invoke(page: Int, character: String) =
        if (character.length < MIN_NAME_LENGTH ) {
            Result.Error(ErrorEntity.InputError.EmailError)
        } else {
            repository.requestMoreCharacters(page, character)
        }
}