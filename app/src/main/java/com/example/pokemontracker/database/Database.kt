package com.example.pokemontracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Pokemon::class], exportSchema = false, version = 5)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}

fun getDatabase(context: Context) = Room.databaseBuilder(context,  PokemonDatabase::class.java,
    "pokemon-db")
    .fallbackToDestructiveMigration()
    .build()