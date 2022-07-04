package com.example.searchapp.domain.use_cases

import com.example.searchapp.domain.ErrorEntity
import com.example.searchapp.domain.Result
import com.example.searchapp.domain.model.info.Info
import com.example.searchapp.domain.repositories.CharacterRepository
import javax.inject.Inject

class GetMoreCharacters @Inject constructor(
    private val repository: CharacterRepository,
) {
    suspend operator fun invoke(page: Int = Info.DEFAULT_PAGE_SIZE, character: String): Result<Info> =
        if (character.length < 2 ) {
            Result.Error(ErrorEntity.InputError.EmailError)
        } else {
            repository.requestMoreCharacters(page, character)
        }
}