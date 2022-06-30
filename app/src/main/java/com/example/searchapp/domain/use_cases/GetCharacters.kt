package com.example.searchapp.domain.use_cases

import com.example.searchapp.data.Result
import com.example.searchapp.domain.model.characters.Characters
import com.example.searchapp.domain.repositories.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class GetCharacters @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke(name: String): Flow<Result<List<Characters>>> {
        // Handling error for name
        return characterRepository.getCharacters(name).filter { !it.data.isNullOrEmpty() }
    }
}