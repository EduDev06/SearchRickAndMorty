package com.example.searchapp.domain.use_cases

import com.example.searchapp.domain.model.characters.Character
import com.example.searchapp.domain.repositories.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class GetCharacters @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke(name: String): Flow<List<Character>> {
        return characterRepository.getCharacters(name).filter { it.isNotEmpty() }
    }
}