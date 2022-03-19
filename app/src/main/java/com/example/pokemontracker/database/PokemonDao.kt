package com.example.pokemontracker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PokemonDao {
    @Query("SELECT * from pokemon")
    suspend fun getAllPokemon(): List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE num IS :num LIMIT 1")
    suspend fun findByPokedexNumber(num: Int): Pokemon

    @Insert
    suspend fun insertAll(vararg pokemon: Pokemon)

    @Update
    suspend fun updatePokemon(pokemon: Pokemon)

    @Query("SELECT * FROM pokemon WHERE isFavorite IS 1")
    suspend fun getAllFavorites(): List<Pokemon>
}