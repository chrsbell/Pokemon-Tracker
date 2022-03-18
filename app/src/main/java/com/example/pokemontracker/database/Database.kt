package com.example.pokemontracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Pokemon::class, Favorite::class], exportSchema = false, version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun favoriteDao(): FavoriteDao
}

fun getDatabase(context: Context) = Room.databaseBuilder(context,  PokemonDatabase::class.java,
    "pokemon-db")
    .build()