package com.example.searchapp.data.source.local.daos

import androidx.room.*
import com.example.searchapp.data.Result
import com.example.searchapp.data.source.local.model.characters.CachedCharacters
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CharactersDao {

    @Transaction
    @Query("SELECT * FROM characters")
    abstract fun getCharacters(): Flow<Result<List<CachedCharacters>>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCharacter(character: CachedCharacters)
}