package com.example.searchapp.domain.use_cases

import com.example.searchapp.util.ErrorEntity
import com.example.searchapp.util.Result
import com.example.searchapp.domain.repositories.CharacterRepository
import javax.inject.Inject

const val MIN_NAME_LENGTH = 2

class GetMoreCharacters @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(page: Int, input: String) =
        if (input.length < MIN_NAME_LENGTH ) {
            Result.Error(ErrorEntity.InputError.NameError)
        } else {
            repository.requestMoreCharacters(page, input)
        }
}