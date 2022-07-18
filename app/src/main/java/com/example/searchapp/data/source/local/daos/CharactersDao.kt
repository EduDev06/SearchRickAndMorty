package com.example.searchapp.data.source.local.daos

import androidx.room.*
import com.example.searchapp.data.source.local.model.characters.CachedCharacters
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDao {

    @Transaction
    @Query("SELECT * FROM characters WHERE name LIKE '%' || :name || '%' LIMIT :limit OFFSET :offset")
    fun getCharacters(name: String, limit: Int, offset: Int): Flow<List<CachedCharacters>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: List<CachedCharacters>)
}