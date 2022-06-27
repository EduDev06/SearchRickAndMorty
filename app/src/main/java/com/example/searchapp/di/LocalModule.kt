package com.example.searchapp.di

import android.content.Context
import androidx.room.Room
import com.example.searchapp.data.source.local.Cache
import com.example.searchapp.data.source.local.RickAndMortyDatabase
import com.example.searchapp.data.source.local.RoomCache
import com.example.searchapp.data.source.local.daos.CharactersDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val DB_NAME = "rickAndMorty_db"

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalModule {

    @Binds
    abstract fun bindCache(cache: RoomCache): Cache

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext app: Context): RickAndMortyDatabase {
            return Room.databaseBuilder(
                app,
                RickAndMortyDatabase::class.java,
                DB_NAME
            ).build()
        }

        @Provides
        fun provideCharacterDao(db: RickAndMortyDatabase): CharactersDao = db.characterDao()
    }
}