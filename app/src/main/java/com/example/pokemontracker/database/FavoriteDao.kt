package com.example.pokemontracker.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    suspend fun getAllFavorites(): List<Favorite>

    @Insert
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorites WHERE num = :num")
    suspend fun findByNum(num: Int): Favorite
}