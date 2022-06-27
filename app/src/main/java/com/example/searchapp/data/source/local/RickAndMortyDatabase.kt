package com.example.searchapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.searchapp.data.source.local.daos.CharactersDao
import com.example.searchapp.data.source.local.model.characters.CachedCharacters

@Database(
    entities = [
        CachedCharacters::class
    ],
    version = 1
)
abstract class RickAndMortyDatabase: RoomDatabase() {
    abstract fun characterDao(): CharactersDao
}